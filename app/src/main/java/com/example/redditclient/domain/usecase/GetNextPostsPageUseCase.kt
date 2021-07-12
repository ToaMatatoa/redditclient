package com.example.redditclient.domain.usecase

import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.domain.Repository
import io.reactivex.Observable

class GetNextPostsPageUseCase(private val repository: Repository) {
    fun getNextPostsPage(name: String): Observable<List<ResponseData.MainData.Children.Data>> {
        return repository.getNextPostsPage(name)
    }
}