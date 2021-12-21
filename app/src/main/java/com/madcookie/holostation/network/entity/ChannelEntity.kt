package com.madcookie.holostation.network.entity

import com.google.gson.annotations.SerializedName

data class PageInfo(
    @SerializedName("totalResult")
    val totalResult: Int
)

data class ChannelEntity(
    @SerializedName("pageInfo")
    val pageInfo: PageInfo
)