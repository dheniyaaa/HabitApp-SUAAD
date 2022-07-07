package com.dicoding.habitapp.ui.random

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.pager_item, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        //TODO 14 : Create view and bind data to item view
        val titleHabit = itemView.findViewById<TextView>(R.id.tv_title_pagerItem)
        val startTime = itemView.findViewById<TextView>(R.id.tv_start_time_pagerItem)
        val minutes = itemView.findViewById<TextView>(R.id.tv_minutes_pagerItem)
        val ivPriority = itemView.findViewById<ImageView>(R.id.priority_level_pagerItem)
        val btnCountDown = itemView.findViewById<Button>(R.id.btn_open_count_down_pagerItem)

        fun bind(pageType: PageType, pageData: Habit) {

            val priority = when(pageType){
                PageType.HIGH -> R.drawable.ic_priority_high
                PageType.MEDIUM -> R.drawable.ic_priority_medium
                PageType.LOW -> R.drawable.ic_priority_low
            }

            titleHabit.text = pageData.title
            startTime.text = pageData.startTime
            minutes.text = pageData.minutesFocus.toString()
            ivPriority.setImageResource(priority)
            btnCountDown.setOnClickListener { onClick(pageData) }
        }
    }
}
