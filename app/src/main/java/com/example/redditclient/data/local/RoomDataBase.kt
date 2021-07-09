package com.example.redditclient.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class RoomDB : RoomDatabase() {

    abstract fun localDao(): LocalDataDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDB(context).also { instance = it }
        }

        private fun buildDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java, "redditposts.db"
            )
                .build()
    }
}