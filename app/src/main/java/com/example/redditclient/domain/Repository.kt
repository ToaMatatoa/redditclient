package com.example.redditclient.domain

import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.data.local.LocalDataStore
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

    fun nextPage(name: String): Observable<ResponseData> {
        return remoteDataStore.nextPage(name)
    }

    fun prevPage(name: String): Observable<ResponseData> {
        return remoteDataStore.prevPage(name)
    }

    suspend fun getAllLocalData(): List<FavoritePost> = localDataStore.getAllLocalData()

    suspend fun saveFavoritePost(post: Children.Data) {
        localDataStore.saveFavoritePost(post.toLocalData())
    }

    suspend fun deleteFavoritePost(id: Int) {
        localDataStore.deleteFavoritePost(id)
    }
}