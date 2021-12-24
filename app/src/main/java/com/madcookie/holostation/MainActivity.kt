package com.madcookie.holostation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import com.madcookie.holostation.data.Channel
import com.madcookie.holostation.data.Repository
import com.madcookie.holostation.databinding.ActivityMainBinding
import com.madcookie.holostation.util.Logger
import com.madcookie.holostation.util.readObject
import com.madcookie.holostation.util.toSafe
import com.madcookie.holostation.util.writeObject
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val channelListAdapter = ChannelListAdapter()

    private var getChannelDataJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channelList = kotlin.runCatching {
            readObject<List<Channel>>("test")
        }.onFailure {
            it.printStackTrace()
        }.getOrDefault(Repository.channelList.map { it.copy() })

        Logger.d("read: $channelList")

        binding.listChannel.adapter = channelListAdapter
        channelListAdapter.submitList(channelList)
        ItemTouchHelper(channelListAdapter.ItemTouchCallback()).attachToRecyclerView(binding.listChannel)


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
            for (channel in channelListAdapter.currentList) {
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
                channelListAdapter.submitList(Repository.channelList)
                stopLoading()
            }
        }
    }

    private fun extractVideoId(href: String): String {
        return Regex("v=(.*)").find(href)?.groupValues?.getOrNull(1) ?: ""
    }

    private fun startLoading() {
        binding.loading.visibility = View.VISIBLE
        binding.swipeRefresh.isEnabled = false
        channelListAdapter.isItemClickBlock = true
    }


    private fun stopLoading() {
        binding.loading.visibility = View.INVISIBLE
        binding.swipeRefresh.isEnabled = true
        channelListAdapter.isItemClickBlock = false
    }

    override fun onBackPressed() {
        if (getChannelDataJob == null || getChannelDataJob?.isCancelled.toSafe()) {
            super.onBackPressed()
        } else {
            lifecycleScope.launch {
                getChannelDataJob?.cancelAndJoin()
                stopLoading()
            }
        }
    }

    override fun onDestroy() {
        kotlin.runCatching {
            writeObject("test", channelListAdapter.currentList)
            Logger.d("write: ${channelListAdapter.currentList}")
        }.onFailure {
            it.printStackTrace()
        }
        super.onDestroy()

    }
}