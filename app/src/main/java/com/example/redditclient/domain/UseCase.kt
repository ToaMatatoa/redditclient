package com.example.redditclient.domain

import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import io.reactivex.Observable

class UseCase(private val repository: Repository) {

    fun getPosts(): Observable<ResponseData> {
        return repository.getPosts()
    }

    fun getNextPostsPage(name: String): Observable<ResponseData> {
        return repository.getNextPostsPage(name)
    }

    fun getPrevPostsPage(name: String): Observable<ResponseData> {
        return repository.getPrevPostsPage(name)
    }

    suspend fun getAllFavoritePosts(): List<FavoritePost> = repository.getAllFavoritePosts()

    suspend fun saveFavoritePost(post: Children.Data) {
        repository.saveFavoritePost(post)
    }

    suspend fun deleteFavoritePost(id: Int) {
        repository.deleteFavoritePost(id)
    }
}