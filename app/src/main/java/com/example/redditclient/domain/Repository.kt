package com.example.redditclient.domain

import com.example.redditclient.data.local.model.LocalData
import com.example.redditclient.data.local.model.LocalDataStore
import com.example.redditclient.data.remote.RemoteDataStore
import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import io.reactivex.Observable

class Repository(
    private val remoteDataStore: RemoteDataStore,
    private val localDataStore: LocalDataStore
) {

    fun getTopEntries(): Observable<ResponseData> {
        return remoteDataStore.getTopEntries()
    }

    fun nextPage(name: String): Observable<Children> {
        return remoteDataStore.nextPage(name)
    }

    fun prevPage(name: String): Observable<Children> {
        return remoteDataStore.prevPage(name)
    }

    suspend fun getAllLocalData(): List<LocalData> = localDataStore.getAllLocalData()

    suspend fun saveLocalData(localData: List<LocalData>) {
        localDataStore.saveLocaleData(localData)
    }
}