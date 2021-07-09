package com.example.redditclient.data.local

import com.example.redditclient.data.local.model.LocalData

class LocalDataStore(private val localDataDao: LocalDataDao) {

    fun getAllLocalData(): List<LocalData> = localDataDao.getLocalPosts()

    fun saveLocaleData(localData: List<LocalData>) {
        localDataDao.saveLocalPosts(localData)
    }
}