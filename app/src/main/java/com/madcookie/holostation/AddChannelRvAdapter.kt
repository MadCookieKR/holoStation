package com.madcookie.holostation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.madcookie.holostation.data.Channel
import com.madcookie.holostation.databinding.ItemChannelSelectableBinding
import com.madcookie.holostation.util.createDefaultDiffUtil


class AddChannelRvAdapter : ListAdapter<Channel, AddChannelRvAdapter.ViewHolder>(createDefaultDiffUtil()) {
    class ViewHolder(val binding: ItemChannelSelectableBinding) : RecyclerView.ViewHolder(binding.root)

    val selected = mutableSetOf<Channel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChannelSelectableBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channel = getItem(position)
        holder.binding.channelName.text = channel.name
        holder.binding.circleImageView.setImageResource(channel.profileImage)
        holder.binding.check.isChecked = selected.contains(channel)
        holder.binding.check.setOnClickListener {
            if (holder.binding.check.isChecked) {
                selected.add(channel)
            }else{
                selected.remove(channel)
            }
        }
    }
}