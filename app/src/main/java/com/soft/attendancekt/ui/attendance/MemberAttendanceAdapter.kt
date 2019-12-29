package com.soft.attendancekt.ui.attendance

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.soft.attendancekt.BR
import com.soft.attendancekt.R
import com.soft.attendancekt.model.entity.tuple.MemberAttendance

class MemberAttendanceAdapter : PagedListAdapter<MemberAttendance, MemberAttendanceAdapter.MemberAttendanceViewHolder>(
    DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MemberAttendance>() {
            override fun areItemsTheSame(
                oldItem: MemberAttendance,
                newItem: MemberAttendance
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MemberAttendance,
                newItem: MemberAttendance
            ): Boolean {
                return oldItem == newItem
            }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberAttendanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.layout_member_attendance, parent, false)
        return MemberAttendanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberAttendanceViewHolder, position: Int) {
        getItem(position)?.also { holder.bind(it) }
    }

    inner class MemberAttendanceViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: MemberAttendance) {
            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()
        }

    }
}