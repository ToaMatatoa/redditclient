package com.example.redditclient.domain

import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import io.reactivex.Observable

class UseCase(private val repository: Repository) {

    fun getTopEntries(): Observable<ResponseData> {
        return repository.getTopEntries()
    }

    fun getNextPosts(name: String): Observable<ResponseData> {
        return repository.nextPage(name)
    }

    fun getPrevPosts(name: String): Observable<ResponseData> {
        return repository.prevPage(name)
    }

    suspend fun getFavorites(): List<FavoritePost> = repository.getAllLocalData()

    suspend fun saveLocalData(post: Children.Data) {
        repository.saveFavoritePost(post)
    }

    suspend fun deleteFavoritePost(id: Int) {
        repository.deleteFavoritePost(id)
    }
}