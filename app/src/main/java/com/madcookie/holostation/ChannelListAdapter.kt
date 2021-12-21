package com.madcookie.holostation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madcookie.holostation.network.Channel
import com.madcookie.holostation.databinding.ItemChannelBinding
import com.madcookie.holostation.network.api.YoutubeApi
import com.madcookie.holostation.util.createDiffUtil

class ChannelListAdapter : ListAdapter<Channel, ChannelListAdapter.ViewHolder>(createDiffUtil()) {

    class ViewHolder(val binding : ItemChannelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channel = getItem(position)
        holder.binding.also {
            it.channelName.text = channel.name
        }

    }
}