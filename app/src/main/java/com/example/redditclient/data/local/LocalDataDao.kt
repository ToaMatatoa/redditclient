package com.example.redditclient.data.local

import androidx.room.*
import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.data.local.model.FavoritePost.Companion.COLUMN_ID
import com.example.redditclient.data.local.model.FavoritePost.Companion.TABLE_NAME

@Dao
interface LocalDataDao {

    @Query(
        "SELECT * FROM $TABLE_NAME"
    )
    suspend fun getAllFavoritePosts(): List<FavoritePost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavoritePost(favoritePost: FavoritePost)

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :id")
    suspend fun deleteFavouritePost(id: Int)
}