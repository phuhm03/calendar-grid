package com.fuhm.calendar_grid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.fuhm.calendar_grid.R
import com.fuhm.calendar_grid.databinding.ItemCarlendarGridBinding
import com.fuhm.calendar_grid.data.model.CalendarGrid

class CalendarGridAdapter : ListAdapter<CalendarGrid, CalendarGridAdapter.CalendarGridViewHolder>(CalendarGridDiffCallback()) {
    private var selectedPosition = RecyclerView.NO_POSITION

    inner class CalendarGridViewHolder(private val binding: ItemCarlendarGridBinding) :
        ViewHolder(binding.root) {
        fun onBind(calendarGrid: CalendarGrid) {
            binding.run {
                tvDay.text = if (calendarGrid.day == 0) "" else calendarGrid.day.toString()
                indicatorToday.setBackgroundResource(getBackgroundIndicator(calendarGrid))
                root.run {
                    isSelected = adapterPosition == selectedPosition
                    setOnClickListener {
                        if (calendarGrid.day != 0) {
                            toggleSelectedPosition()
                        }
                    }
                }
            }
        }

        private fun getBackgroundIndicator(calendarGrid: CalendarGrid) : Int {
            return if (calendarGrid.isToday()) {
                R.drawable.bg_indicator
            } else {
                R.drawable.bg_indicator_trasparent
            }
        }

        private fun toggleSelectedPosition() {
            val previousPosition = selectedPosition
            selectedPosition = adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    class CalendarGridDiffCallback : DiffUtil.ItemCallback<CalendarGrid>() {
        override fun areItemsTheSame(oldItem: CalendarGrid, newItem: CalendarGrid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CalendarGrid, newItem: CalendarGrid): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarGridViewHolder {
        val binding = ItemCarlendarGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.root.layoutParams.height = parent.measuredWidth / 7
        return CalendarGridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarGridViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun submitList(list: List<CalendarGrid>?) {
        super.submitList(list)
        selectedPosition = list?.indexOfFirst { it.isToday() } ?: RecyclerView.NO_POSITION
    }
}