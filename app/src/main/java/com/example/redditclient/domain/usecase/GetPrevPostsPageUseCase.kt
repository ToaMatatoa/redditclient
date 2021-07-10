package com.example.redditclient.domain.usecase

import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.domain.Repository
import io.reactivex.Observable

class GetPrevPostsPageUseCase(private val repository: Repository) {

    fun getPrevPostsPage(name: String): Observable<ResponseData> {
        return repository.getPrevPostsPage(name)
    }
}