package com.example.redditclient.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.redditclient.data.local.model.FavoritePost.Companion.COLUMN_ID

@Entity(tableName = FavoritePost.Companion.TABLE_NAME)
data class FavoritePost(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
 //   var id: Int,
    var numLikes: Int,
    var author: String?,
    var subreddit: String?,
    var numComments: Int?,
    var thumbnail: String?,
    var url: String?,
    var name: String?,
    var title: String?,
    var timeAgo: String?,
    var info: String = "post by $author in $subreddit $timeAgo"
) {
    object Companion {
        const val TABLE_NAME = "postslocaldata"
        const val COLUMN_ID = "id"
    }
}





