package com.madcookie.holostation.network

import com.madcookie.holostation.util.DiffUtilData

data class Channel (
    val name : String
) : DiffUtilData<Channel> {
    override fun areItemsTheSame(newItem: Channel): Boolean {
        return this.name == newItem.name
    }

    override fun areContentsTheSame(newItem: Channel): Boolean {
        return this.name == newItem.name
    }
}