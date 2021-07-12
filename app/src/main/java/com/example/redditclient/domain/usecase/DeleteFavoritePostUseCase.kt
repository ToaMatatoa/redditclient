package com.example.redditclient.domain.usecase

import com.example.redditclient.domain.Repository

class DeleteFavoritePostUseCase(private val repository: Repository) {
    suspend fun deleteFavoritePost(id: String) {
        repository.deleteFavoritePost(id)
    }
}