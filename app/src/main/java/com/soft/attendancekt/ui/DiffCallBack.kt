package com.soft.attendancekt.ui

import androidx.recyclerview.widget.DiffUtil
import com.soft.attendancekt.model.entity.Member

class DiffCallBack : DiffUtil.ItemCallback<Member>() {
    override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
        return oldItem == newItem
    }

}