package com.madcookie.holostation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.madcookie.holostation.data.Channel
import com.madcookie.holostation.databinding.ActivityMainBinding
import com.madcookie.holostation.util.readObject
import com.madcookie.holostation.util.toSafe
import com.madcookie.holostation.util.writeObject
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val channelListAdapter = ChannelListRvAdapter(object : ChannelListRvAdapterListener {
        override fun onClickAddChannel() {
            (supportFragmentManager.fragmentFactory.instantiate(classLoader, AddChannelDialog::class.java.name)
                    as DialogFragment).show(supportFragmentManager, null)
        }

    })

    private var getChannelDataJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channelList = kotlin.runCatching {
            readObject<List<Channel>>(CHANNEL_LIST_OBJECT)
        }.onFailure {
            it.printStackTrace()
        }.getOrDefault(emptyList())

        binding.listChannel.adapter = channelListAdapter
        channelListAdapter.submitList(channelList)
        ItemTouchHelper(channelListAdapter.ItemTouchCallback()).attachToRecyclerView(binding.listChannel)

        supportFragmentManager.fragmentFactory = AppFragmentFactory(object : AddChannelDialogListener {
            override fun onConfirm(added: List<Channel>) {
                channelListAdapter.submitList(channelListAdapter.currentList + added)
            }

        }, { channelListAdapter.currentList })

        updateChannelList()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            updateChannelList()
        }


    }

    private fun updateChannelList() {
        startLoading()

        getChannelDataJob?.cancel()
        getChannelDataJob = lifecycleScope.launch(Dispatchers.IO) {
            val channelList = channelListAdapter.currentList.map { it.copy() }
            for (channel in channelList) {
                yield()
                channel.isLive = Jsoup.connect("https://www.youtube.com/channel/${channel.id}/live")
                    .get()
                    .takeIf { !it.toString().contains("LIVE_STREAM_OFFLINE") }
                    ?.select("link[rel=canonical]")
                    ?.attr("href")
                    ?.also { channel.videoId = extractVideoId(it) }
                    ?.contains("/watch?v=") ?: false
            }

            withContext(Dispatchers.Main) {
                channelListAdapter.submitList(channelList.filter { channelListAdapter.currentList.contains(it) })
                stopLoading()
            }
        }
    }

    private fun extractVideoId(href: String): String {
        return Regex("v=(.*)").find(href)?.groupValues?.getOrNull(1) ?: ""
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun startLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.swipeRefresh.isEnabled = false
        channelListAdapter.isItemClickBlock = true
        binding.loading.setOnTouchListener { v, event ->
            false
        }
    }


    private fun stopLoading() {
        binding.loading.visibility = View.INVISIBLE
        binding.swipeRefresh.isEnabled = true
        channelListAdapter.isItemClickBlock = false
    }

    override fun onBackPressed() {
        if (getChannelDataJob == null || !getChannelDataJob?.isActive.toSafe()) {
            super.onBackPressed()
        } else {
            lifecycleScope.launch {
                getChannelDataJob?.cancelAndJoin()
                stopLoading()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        kotlin.runCatching {
            channelListAdapter.currentList.forEach { it.isLive = false }
            writeObject(CHANNEL_LIST_OBJECT, channelListAdapter.currentList)
        }.onFailure {
            it.printStackTrace()
        }
    }

    companion object {
        private const val CHANNEL_LIST_OBJECT = "CHANNEL_LIST_OBJECT"
    }

}