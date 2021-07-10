package com.example.redditclient.domain

import com.example.redditclient.data.local.model.LocalData
import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children
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

    suspend fun getAllLocalData(): List<LocalData> = repository.getAllLocalData()

    suspend fun saveLocalData(localData: List<LocalData>) {
        repository.saveLocalData(localData)
    }
}