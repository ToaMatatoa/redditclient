package com.example.redditclient.domain.usecase

import com.example.redditclient.data.remote.model.ResponseData
import com.example.redditclient.domain.Repository

class SaveFavoritePostUseCase(private val repository: Repository) {

    suspend fun saveFavoritePost(post: ResponseData.MainData.Children.Data) {
        repository.saveFavoritePost(post)
    }
}