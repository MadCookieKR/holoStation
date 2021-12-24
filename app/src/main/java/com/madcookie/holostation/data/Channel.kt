package com.madcookie.holostation.data

import androidx.annotation.DrawableRes
import com.madcookie.holostation.util.DiffUtilData
import java.io.Serializable

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
) : Serializable