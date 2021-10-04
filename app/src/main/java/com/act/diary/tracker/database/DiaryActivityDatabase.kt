package com.act.diary.tracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DailyActivity::class], version = 1, exportSchema = true)
abstract class DiaryActivityDatabase : RoomDatabase() {

    abstract val diaryActivityDatabaseDao: DiaryActivityDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryActivityDatabase? = null

        fun getInstance(context: Context): DiaryActivityDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiaryActivityDatabase::class.java,
                        "diary_activity_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }

}