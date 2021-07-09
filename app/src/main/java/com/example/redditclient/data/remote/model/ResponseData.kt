package com.example.redditclient.data.remote.model

import com.google.gson.annotations.SerializedName
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

data class ResponseData(
    @SerializedName("data") var data: MainData
) {

    data class MainData(
        @SerializedName("children") var children: Children
    ) {

        data class Children(
            @SerializedName("data") var data: Data
        ) {

            data class Data(
                @SerializedName("subreddit") var subreddit: String,
                @SerializedName("author") var author: String,
                @SerializedName("num_comments") var numComments: Int,
                @SerializedName("thumbnail") var thumbnail: String,
                @SerializedName("url") var url: String,
                @SerializedName("score") var numLikes: Int,
                @SerializedName("name") var name: String,
                @SerializedName("title") var title: String,
                @SerializedName("created_utc") var timeCreation: Long
            ) {
                var timeAgo = PrettyTime(Locale.ENGLISH).format(Date(timeCreation * 1000))
                var info: String = "post by $author in $subreddit $timeAgo"
            }
        }
    }
}
