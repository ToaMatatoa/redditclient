package com.example.redditclient.domain.usecase

import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import com.example.redditclient.domain.Repository
import io.reactivex.Observable

class GetPostsUseCase(private val repository: Repository) {

    fun getPosts(): Observable<List<Data>> {
        return repository.getPosts()
    }
}