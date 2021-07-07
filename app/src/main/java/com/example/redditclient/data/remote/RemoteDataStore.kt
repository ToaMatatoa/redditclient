package com.example.redditclient.data.remote

import com.example.redditclient.data.remote.model.ResponseData
import io.reactivex.Observable

class RemoteDataStore {

    private val apiService by lazy { RetrofitClient.redditPostsService() }

    fun getTopEntries(): Observable<ResponseData> {
        return apiService.getTopEntries()
    }

    fun nextPage(name: String): Observable<ResponseData> {
        return apiService.nextPage(name)
    }

    fun prevPage(name: String): Observable<ResponseData> {
        return apiService.prevPage(name)
    }
}