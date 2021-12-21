package com.madcookie.holostation.data

import androidx.annotation.DrawableRes
import com.madcookie.holostation.util.DiffUtilData

enum class Group(msg: String) {
    EN("en"), JP("jp")
}

enum class Generation(msg : String){
    GEN0("0Gen"),
    GEN1("1Gen"),
    GEN2("2Gen"),
    GEN3("3Gen"),
    GEN4("4Gen"),
    GEN5("5Gen"),
    GEN6("6Gen"),
    HOPE("Project: Hope"),
    GAMERS("Hololive GAMERS")
}

data class VChannel(
    val id: String,
    val name: String,
    var isLive: Boolean = false,
    val group: Group,
    val gen: Generation,
    @DrawableRes val profileImage: Int
) : DiffUtilData<VChannel> {
    override fun areItemsTheSame(newItem: VChannel): Boolean {
        return id == newItem.id
    }

    override fun areContentsTheSame(newItem: VChannel): Boolean {
        return isLive == newItem.isLive
    }

}