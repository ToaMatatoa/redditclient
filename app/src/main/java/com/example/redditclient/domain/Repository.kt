package com.example.redditclient.domain

import com.example.redditclient.data.remote.RemoteDataStore
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import io.reactivex.Observable

class Repository(private val remoteDataStore: RemoteDataStore) {

    fun getTopEntries(): Observable<List<Children>> {
        return remoteDataStore.getTopEntries()
    }

    fun nextPage(name: String): Observable<Children> {
        return remoteDataStore.nextPage(name)
    }

    fun prevPage(name: String): Observable<Children> {
        return remoteDataStore.prevPage(name)
    }
}