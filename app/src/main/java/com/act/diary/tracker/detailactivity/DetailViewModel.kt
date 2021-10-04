package com.act.diary.tracker.detailactivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.act.diary.tracker.database.DailyActivity
import com.act.diary.tracker.database.DiaryActivityDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val key: Long = 0L,
    val database: DiaryActivityDatabaseDao
) : ViewModel() {

    private val _navigateToList = MutableLiveData<Boolean?>()
    val navigateToList: LiveData<Boolean?>
        get() = _navigateToList

    private val _data = MutableLiveData<DailyActivity?>()
    val data: LiveData<DailyActivity?>
        get() = _data

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.Default) {
            val item = database.get(key) ?: return@launch
            _data.postValue(item)
        }
    }

    fun doneNavigating() {
        _navigateToList.value = null
    }


    fun onSetDiaryActivity(type: String, comment: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val item = database.get(key) ?: return@launch
            item.type = type
            item.comment = comment
            item.endTimeMilli = System.currentTimeMillis()
            item.state = 1
            database.update(item)
            _navigateToList.postValue(true)
        }
    }
}