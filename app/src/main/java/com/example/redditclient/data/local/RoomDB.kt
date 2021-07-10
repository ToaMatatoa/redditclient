package com.example.redditclient.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.redditclient.data.local.model.FavoritePost

@Database(entities = [FavoritePost::class], version = 1)
abstract class RoomDB : RoomDatabase() {

    abstract fun dataDao(): LocalDataDao
}