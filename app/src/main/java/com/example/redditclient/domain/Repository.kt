package com.example.redditclient.domain

import com.example.redditclient.data.local.LocalDataStore
import com.example.redditclient.data.remote.RemoteDataStore
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import io.reactivex.Observable

class Repository(
    private val remoteDataStore: RemoteDataStore,
    private val localDataStore: LocalDataStore
) {

    fun getTopEntries(): Observable<List<Data>> {
//        val remoteData = remoteDataStore.getTopEntries()
//        val localData = remoteData.map {it.toLocalData}
//        localDataStore.saveLocaleData(localData)
        return remoteDataStore.getTopEntries()
    }

    fun nextPage(name: String): Observable<Children> {
        return remoteDataStore.nextPage(name)
    }

    fun prevPage(name: String): Observable<Children> {
        return remoteDataStore.prevPage(name)
    }
}