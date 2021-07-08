package com.example.redditclient.data.remote

import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import io.reactivex.Observable

class RemoteDataStore {

    private val apiService by lazy { RetrofitClient.redditPostsService() }

    fun getTopEntries(): Observable<List<Children>> {
        return apiService.getTopEntries()
    }

    fun nextPage(name: String): Observable<Children> {
        return apiService.nextPage(name)
    }

    fun prevPage(name: String): Observable<Children> {
        return apiService.prevPage(name)
    }
}