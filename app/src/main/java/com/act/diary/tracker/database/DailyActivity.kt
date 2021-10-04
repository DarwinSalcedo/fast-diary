package com.act.diary.tracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_activity_table")
data class DailyActivity(
    @PrimaryKey(autoGenerate = true)
    var activityId: Long = 0L,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = 0L,

    @ColumnInfo(name = "type_activity")
    var type: String = "-",

    @ColumnInfo(name = "comment")
    var comment: String = "",

    @ColumnInfo(name = "state")
    var state: Int = 0

)