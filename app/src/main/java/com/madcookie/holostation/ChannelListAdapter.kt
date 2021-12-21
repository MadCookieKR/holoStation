package com.madcookie.holostation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madcookie.holostation.data.VChannel
import com.madcookie.holostation.databinding.ItemChannelBinding
import com.madcookie.holostation.util.createDiffUtil

class ChannelListAdapter : ListAdapter<VChannel, ChannelListAdapter.ViewHolder>(createDiffUtil()) {

    class ViewHolder(val binding: ItemChannelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channel = getItem(position)
        holder.binding.also {
            it.channelName.text = channel.name
            it.iconLive.visibility = if (channel.isLive) View.VISIBLE else View.INVISIBLE
            it.circleImageView.setImageResource(channel.profileImage)
        }

    }
}