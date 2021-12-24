package com.madcookie.holostation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.madcookie.holostation.data.Channel
import com.madcookie.holostation.databinding.ItemChannelBinding
import com.madcookie.holostation.util.Logger
import java.util.*


class ChannelListAdapter : RecyclerView.Adapter<ChannelListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemChannelBinding) : RecyclerView.ViewHolder(binding.root)

    var isItemClickBlock = false

    var currentList: MutableList<Channel> = mutableListOf()
    private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChannelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val channel = currentList[position]
        holder.binding.also { binding ->
            binding.channelName.text = channel.name
            binding.iconLive.visibility = if (channel.isLive) View.VISIBLE else View.INVISIBLE
            binding.circleImageView.setImageResource(channel.profileImage)
            binding.description.text = "${channel.group.msg} / ${channel.gen.msg}"

            binding.root.setOnClickListener { v ->
                if (!isItemClickBlock && channel.isLive && channel.videoId.isNotBlank()) {
                    goToYoutube(v.context, channel.videoId)
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun submitList(newChannelList: List<Channel>) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return currentList.size
            }

            override fun getNewListSize(): Int {
                return newChannelList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return currentList[oldItemPosition].id == currentList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return currentList[oldItemPosition].isLive == currentList[newItemPosition].isLive
            }
        }).dispatchUpdatesTo(this)
        this.currentList = newChannelList.toMutableList()
    }

    private fun goToYoutube(context: Context, videoId: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$videoId")
        )
        try {
            context.startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            context.startActivity(webIntent)
        }
    }


    inner class ItemTouchCallback : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            return makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.START or ItemTouchHelper.END
            )
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            Collections.swap(currentList, viewHolder.adapterPosition, target.adapterPosition)
            notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            currentList.removeAt(viewHolder.adapterPosition)
            notifyItemRemoved(viewHolder.adapterPosition)
        }
    }


}