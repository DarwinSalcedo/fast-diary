package com.act.diary.tracker.detailactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.act.diary.tracker.database.DiaryActivityDatabaseDao

class DetailViewModelFactory(
    private val sleepNightKey: Long,
    private val dataSource: DiaryActivityDatabaseDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(sleepNightKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}