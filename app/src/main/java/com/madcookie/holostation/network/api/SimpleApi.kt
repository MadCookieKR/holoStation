package com.madcookie.holostation.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface SimpleApi {

    @HEAD("channel/{channel_id}/live")
    suspend fun getLiveVideo(@Path("channel_id") channelId : String): Response<Void>

    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

            Retrofit.Builder()
                .baseUrl("https://www.youtube.com/")
                .client(client)
                .build()
        }

        val instance by lazy {
            retrofit.create(SimpleApi::class.java)
        }
    }
}