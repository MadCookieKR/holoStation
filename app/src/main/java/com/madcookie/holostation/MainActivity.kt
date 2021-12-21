package com.madcookie.holostation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = ChannelListAdapter()
        binding.listChannel.adapter = adapter
        adapter.submitList(Repository.channelList.map { it.copy() })

        binding.requestBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                for (channel in Repository.channelList) {
                    channel.isLive = Jsoup.connect("https://www.youtube.com/channel/${channel.id}/live")
                        .get()
                        .select("link[rel=canonical]")
                        .attr("href")
                        .contains("/watch?v=")
                    Log.d("monster", channel.toString())
                }

                withContext(Dispatchers.Main) {
                    adapter.submitList(Repository.channelList)
                }
            }
        }

    }
}