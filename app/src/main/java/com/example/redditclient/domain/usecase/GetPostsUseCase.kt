package com.example.redditclient.domain.usecase

import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import com.example.redditclient.domain.Repository
import io.reactivex.Observable

class GetPostsUseCase(private val repository: Repository) {

    fun getPosts(): Observable<ResponseData> {
        return repository.getPosts()
    }
}