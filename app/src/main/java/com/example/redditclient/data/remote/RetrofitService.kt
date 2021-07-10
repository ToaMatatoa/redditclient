package com.example.redditclient.data.remote

import com.example.redditclient.data.remote.model.ResponseData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("top.json?limit=50")
    fun getPosts(): Observable<ResponseData>

    @GET("top.json?limit=50&")
    fun getNextPostsPage(@Query("after") name: String): Observable<ResponseData>

    @GET("top.json?limit=50&")
    fun getPrevPostsPage(@Query("before") name: String): Observable<ResponseData>
}