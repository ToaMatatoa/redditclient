package com.example.redditclient.data.remote

import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("top.json?limit=50")
    fun getTopEntries(): Observable<List<Data>>

    @GET("top.json?limit=50&")
    fun nextPage(@Query("after") name: String): Observable<Children>

    @GET("top.json?limit=50&")
    fun prevPage(@Query("before") name: String): Observable<Children>
}