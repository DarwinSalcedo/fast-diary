package com.act.diary.tracker.activitylist

import android.app.Application
import androidx.lifecycle.*
import com.act.diary.tracker.database.DailyActivity
import com.act.diary.tracker.database.DiaryActivityDatabaseDao
import kotlinx.coroutines.launch

class ListViewModel(
    val database: DiaryActivityDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var data = MutableLiveData<DailyActivity?>()

    val listRecords = database.getAll()

    private var _showSnackbarEvent = MutableLiveData<Boolean>().apply { value = false }
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    private val _navigateToDetail = MutableLiveData<DailyActivity>()

    val navigateToDetail: LiveData<DailyActivity>
        get() = _navigateToDetail


    val showClearButton = Transformations.map(listRecords) {
        it.isNotEmpty()
    }

    fun onStartTracking(type: String) {
        viewModelScope.launch {
            val newNight = DailyActivity(type = type)
            insert(newNight)
            data.value = getLastInserted()
        }
    }

    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }


    fun doneNavigating() {
        _navigateToDetail.value = null
    }

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            data.value = getLastInserted()
        }
    }

    private suspend fun getLastInserted(): DailyActivity? {
        var activity = database.getLastInserted()
        if (activity?.endTimeMilli != activity?.startTimeMilli) {
            activity = null
        }
        return activity
    }


    private suspend fun insert(value: DailyActivity) {
        database.insert(value)
    }

}


