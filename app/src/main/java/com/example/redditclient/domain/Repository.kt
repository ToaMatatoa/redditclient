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

    fun getPosts(): Observable<ResponseData> {
        return remoteDataStore.getPosts()
    }

    fun getNextPostsPage(name: String): Observable<ResponseData> {
        return remoteDataStore.getNextPostsPage(name)
    }

    fun getPrevPostsPage(name: String): Observable<ResponseData> {
        return remoteDataStore.getPrevPostsPage(name)
    }

    suspend fun getAllFavoritePosts(): List<FavoritePost> = localDataStore.getAllFavoritePosts()

    suspend fun saveFavoritePost(post: Children.Data) {
        localDataStore.saveFavoritePost(post.toLocalData())
    }

    suspend fun deleteFavoritePost(id: Int) {
        localDataStore.deleteFavoritePost(id)
    }
}