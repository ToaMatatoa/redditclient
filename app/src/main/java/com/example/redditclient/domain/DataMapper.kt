package com.example.redditclient.domain

import com.example.redditclient.data.local.model.FavoritePost
import com.example.redditclient.data.remote.model.ResponseData.MainData.Children.Data

fun Data.toLocalData() = FavoritePost(
    subreddit = subreddit,
    author = author,
    numComments = numComments,
    thumbnail = thumbnail,
    url = url,
    numLikes = numLikes,
    name = name,
    title = title,
    timeAgo = timeAgo,
    info = info,
    id = id
)