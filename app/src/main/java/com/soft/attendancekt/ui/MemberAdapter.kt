package com.soft.attendancekt.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soft.attendancekt.BR
import com.soft.attendancekt.R
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.util.AdapterItemClickListener

class MemberAdapter() : ListAdapter<Member, MemberAdapter.MemberViewHolder>(DiffCallBack()) {

    lateinit var adapterItemClickListener: AdapterItemClickListener<Member>
    /*set(adapterClick: AdapterItemClickListener<Member>) {
        field = adapterClick
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_layout_list_item, parent, false)
        return MemberViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ViewDataBinding

        constructor(binding: ViewDataBinding) : this(binding.root) {
            this.binding = binding
            binding.root.setOnClickListener {
                adapterItemClickListener.onClick(getItem(adapterPosition))
            }
        }

        fun bind(member: Member) {
            binding.setVariable(BR.obj, member)
            binding.executePendingBindings()
        }

    }

}

