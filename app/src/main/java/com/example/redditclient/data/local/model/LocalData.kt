package com.example.redditclient.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = LocalData.Companion.TABLE_NAME)
data class LocalData(
    @PrimaryKey
    var numOfEntry: Int,
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
    }
}





