package com.example.redditclient.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditclient.data.local.model.LocalData
import com.example.redditclient.data.local.model.LocalData.Companion.TABLE_NAME
import io.reactivex.Observable

@Dao
interface LocalDataDao {

    @Query(
        "SELECT * FROM $TABLE_NAME"
    )
    fun getLocalPosts(): Observable<List<LocalData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLocalPosts(localposts: List<LocalData>)
}