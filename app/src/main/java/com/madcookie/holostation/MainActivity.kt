package com.madcookie.holostation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.madcookie.holostation.data.Repository
import com.madcookie.holostation.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val channelListAdapter = ChannelListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Glide.with(this).load(R.drawable.loading).into(binding.loadingImage)
        binding.listChannel.adapter = channelListAdapter
        channelListAdapter.submitList(Repository.channelList.map { it.copy() })

        updateChannelList()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            updateChannelList()
        }
    }

    private fun updateChannelList() {
        binding.swipeRefresh.isEnabled = false
        binding.loading.visibility = View.VISIBLE
        channelListAdapter.isItemClickBlock = true

        lifecycleScope.launch(Dispatchers.IO) {
            for (channel in Repository.channelList) {
                channel.isLive = Jsoup.connect("https://www.youtube.com/channel/${channel.id}/live")
                    .get()
                    .select("link[rel=canonical]")
                    .attr("href")
                    .also { channel.videoId = extractVideoId(it) }
                    .contains("/watch?v=")
                Log.d("monster", channel.toString())
            }

            withContext(Dispatchers.Main) {
                channelListAdapter.submitList(Repository.channelList)
                binding.loading.visibility = View.INVISIBLE
                binding.swipeRefresh.isEnabled = true
                channelListAdapter.isItemClickBlock = false
            }
        }
    }

    private fun extractVideoId(href: String): String {
        return Regex("v=(.*)").find(href)?.groupValues?.getOrNull(1) ?: ""
    }
}