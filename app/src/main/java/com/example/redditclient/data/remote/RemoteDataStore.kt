package com.example.redditclient.data.remote

import com.example.redditclient.data.remote.model.ResponseData
import io.reactivex.Observable

class RemoteDataStore {

    private val apiService by lazy { RetrofitClient.redditPostsService() }

    fun getPosts(): Observable<ResponseData> {
        return apiService.getPosts()
    }

    fun getNextPostsPage(name: String): Observable<ResponseData> {
        return apiService.getNextPostsPage(name)
    }

    fun getPrevPostsPage(name: String): Observable<ResponseData> {
        return apiService.getPrevPostsPage(name)
    }
}