package com.example.redditclient.domain

import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data
import io.reactivex.Observable

class UseCase (private val repository: Repository) {

    fun getTopEntries(): Observable<ResponseData> {
        return repository.getTopEntries()
    }

    fun nextPage(name: String): Observable<Children> {
        return repository.nextPage(name)
    }

    fun prevPage(name: String): Observable<Children> {
        return repository.prevPage(name)
    }
}