package com.act.diary.tracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DiaryActivityDatabaseDao {

    @Insert
    suspend fun insert(value: DailyActivity)

    @Update
    suspend fun update(value: DailyActivity)

    @Query("SELECT * from daily_activity_table WHERE activityId = :key")
    fun get(key: Long): DailyActivity?

    @Query("DELETE FROM daily_activity_table")
    suspend fun clear()

    @Query("SELECT * FROM daily_activity_table ORDER BY activityId DESC")
    fun getAll(): LiveData<List<DailyActivity>>

    @Query("SELECT * FROM daily_activity_table ORDER BY activityId DESC LIMIT 1")
    suspend fun getLastInserted(): DailyActivity?
}
