package com.example.redditclient.domain

import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
import io.reactivex.Observable

class UseCase (private val repository: Repository) {

    fun getTopEntries(): Observable<List<Children>> {
        return repository.getTopEntries()
    }

    fun nextPage(name: String): Observable<Children> {
        return repository.nextPage(name)
    }

    fun prevPage(name: String): Observable<Children> {
        return repository.prevPage(name)
    }
}