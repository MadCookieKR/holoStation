package com.madcookie.holostation.data

import androidx.annotation.DrawableRes
import com.madcookie.holostation.util.DiffUtilData

enum class Group(val msg: String) {
    EN("EN"), JP("JP")
}

enum class Generation(val msg : String){
    GEN0("Gen0"),
    GEN1("Gen1"),
    GEN2("Gen2"),
    GEN3("Gen3"),
    GEN4("Gen4"),
    GEN5("Gen5"),
    GEN6("Gen6"),
    HOPE("Project: Hope"),
    GAMERS("GAMERS")
}

data class Channel(
    val id: String,
    val name: String,
    var isLive: Boolean = false,
    val group: Group,
    val gen: Generation,
    @DrawableRes val profileImage: Int,
    var videoId : String = ""
) : DiffUtilData<Channel> {
    override fun areItemsTheSame(newItem: Channel): Boolean {
        return id == newItem.id
    }

    override fun areContentsTheSame(newItem: Channel): Boolean {
        return isLive == newItem.isLive
    }

}