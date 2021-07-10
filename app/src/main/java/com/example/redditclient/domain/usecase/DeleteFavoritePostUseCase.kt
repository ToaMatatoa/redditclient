package com.example.redditclient.domain.usecase

import com.example.redditclient.domain.Repository

class DeleteFavoritePostUseCase(private val repository: Repository) {
    suspend fun deleteFavoritePost(id: Int) {
        repository.deleteFavoritePost(id)
    }
}