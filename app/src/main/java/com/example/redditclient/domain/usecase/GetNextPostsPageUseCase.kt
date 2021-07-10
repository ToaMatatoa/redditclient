package com.example.redditclient.domain.usecase

import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.domain.Repository
import io.reactivex.Observable

class GetNextPostsPageUseCase(private val repository: Repository) {
    fun getNextPostsPage(name: String): Observable<ResponseData> {
        return repository.getNextPostsPage(name)
    }
}