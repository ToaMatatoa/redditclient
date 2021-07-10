package com.example.redditclient.data.local

import com.example.redditclient.data.local.model.FavoritePost

class LocalDataStore(private val localDataDao: LocalDataDao) {

    suspend fun getAllFavoritePosts(): List<FavoritePost> = localDataDao.getAllFavoritePosts()

    suspend fun saveFavoritePost(favoritePost: FavoritePost) {
        localDataDao.saveFavoritePost(favoritePost)
    }

    suspend fun deleteFavoritePost(id: Int) {
        localDataDao.deleteFavouritePost(id)
    }
}