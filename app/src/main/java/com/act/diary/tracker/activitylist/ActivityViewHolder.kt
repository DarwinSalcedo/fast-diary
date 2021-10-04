package com.act.diary.tracker.activitylist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.act.diary.tracker.convertLongToDateString
import com.act.diary.tracker.database.DailyActivity
import com.act.diary.tracker.databinding.ItemActivityBinding
import java.util.*

class ActivityViewHolder private constructor(
    private val binding: ItemActivityBinding,
    private val context: Context
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        data: DailyActivity,
        callback: (DailyActivity) -> Unit
    ) {
        with(binding) {
            typeActivity.text = data.type.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            comment.text = data.comment.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            dateStart.text = String.format("Start %s", convertLongToDateString(data.startTimeMilli))
            dateEnd.text = if(data.endTimeMilli == 0L) "Running" else String.format("End %s", convertLongToDateString(data.endTimeMilli))

            stopButton.isEnabled = data.state != 1
            stopButton.setOnClickListener { callback(data) }
        }
    }

    companion object {
        fun from(parent: ViewGroup): ActivityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemActivityBinding.inflate(layoutInflater, parent, false)
            return ActivityViewHolder(binding, parent.context)
        }
    }
}