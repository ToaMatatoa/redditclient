package com.example.redditclient.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BOOKS_URL = "https://www.reddit.com/"
    private var retrofit: Retrofit? = null
    private val logging = HttpLoggingInterceptor()

    fun redditPostsService(): RetrofitService {

        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(logging)
        }.build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BOOKS_URL)
                .client(okHttpClient.newBuilder().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!.create(RetrofitService::class.java)
    }
}