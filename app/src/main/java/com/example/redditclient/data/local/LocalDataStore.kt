package com.example.redditclient.data.local

import com.example.redditclient.data.local.model.LocalData
import io.reactivex.Observable

class LocalDataStore(private val localDataDao: LocalDataDao) {

    fun getAllLocalData(): Observable<List<LocalData>> = localDataDao.getLocalPosts()

    fun saveLocaleData(localData: List<LocalData>) {
        localDataDao.saveLocalPosts(localData)
    }
}