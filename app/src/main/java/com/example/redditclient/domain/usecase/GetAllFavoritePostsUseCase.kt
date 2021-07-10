package com.example.redditclient.domain.usecase

import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.domain.Repository

class GetAllFavoritePostsUseCase(private val repository: Repository) {

    suspend fun getAllFavoritePosts(): List<FavoritePost> = repository.getAllFavoritePosts()
}