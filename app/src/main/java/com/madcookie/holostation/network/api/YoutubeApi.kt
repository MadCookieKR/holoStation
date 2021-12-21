package com.madcookie.holostation.network.api

import com.madcookie.holostation.network.API_KEY
import com.madcookie.holostation.network.entity.ChannelEntity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface YoutubeApi {

    @GET("search?fields=pageInfo/totalResults&type=video&eventType=live")
    suspend fun getLiveStream(
        @Query("key") apiKey: String = API_KEY,
        @Query("channelId") channelId: String
    ) : ChannelEntity

    companion object{

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
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val instance by lazy {
            retrofit.create(YoutubeApi::class.java)
        }
    }

}
