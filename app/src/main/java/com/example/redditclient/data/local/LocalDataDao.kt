package com.example.redditclient.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditclient.data.local.model.LocalData
import com.example.redditclient.data.local.model.LocalData.Companion.TABLE_NAME

@Dao
interface   LocalDataDao {

    @Query(
        "SELECT * FROM $TABLE_NAME"
    )
    suspend fun getAllLocalData(): List<LocalData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllLocalData(localdata: List<LocalData>)
}