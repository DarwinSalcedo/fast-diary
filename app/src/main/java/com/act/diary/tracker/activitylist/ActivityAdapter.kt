package com.act.diary.tracker.activitylist

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.act.diary.tracker.database.DailyActivity

class ActivityAdapter (var data: List<DailyActivity> = emptyList(), val callback: (DailyActivity) -> Unit) :
    RecyclerView.Adapter<ActivityViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(_data: List<DailyActivity>) {
        data = _data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActivityViewHolder {
        return ActivityViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        data[position].let { item ->
            holder.bind(item, callback)
        }
    }

    override fun getItemCount(): Int = data.size
}