package com.example.redditclient.data.remote

import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("top.json?limit=25")
    fun getTopEntries(): Observable<ResponseData>

    @GET("top.json?limit=25&")
    fun nextPage(@Query("after") name: String): Observable<ResponseData>

    @GET("top.json?limit=25&")
    fun prevPage(@Query("before") name: String): Observable<ResponseData>
}