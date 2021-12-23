package com.madcookie.holostation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.madcookie.holostation.data.Repository
import com.madcookie.holostation.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val channelListAdapter = ChannelListAdapter()

    private var getChannelDataJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.listChannel.adapter = channelListAdapter
        channelListAdapter.submitList(Repository.channelList.map { it.copy() })

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
            for (channel in Repository.channelList) {
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
        if (getChannelDataJob == null) {
            super.onBackPressed()
        } else {
            getChannelDataJob?.cancel();
            stopLoading()
        }
    }
}