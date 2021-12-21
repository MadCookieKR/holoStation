package com.madcookie.holostation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.madcookie.holostation.databinding.ActivityMainBinding
import com.madcookie.holostation.network.Repository
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
        adapter.submitList(Repository.channelList)


        //태그파싱밖에 답이 없다

        binding.requestBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val isLive = Jsoup.connect("https://www.youtube.com/channel/UCoSrY_IQQVpmIRZ9Xf-y93g/live")
                    .get()
                    .select("link[rel=canonical]")
                    .also { Log.d("holoLog", "selected: $it") }
                    .attr("href")
                    .also { Log.d("holoLog", "href: $it") }
                    .contains("/watch?v=")

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, isLive.toString(), Toast.LENGTH_SHORT).show()
                }


            }
        }

    }
}