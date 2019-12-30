package com.soft.attendancekt.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soft.attendancekt.BR
import com.soft.attendancekt.R
import com.soft.attendancekt.model.entity.Member

class MemberAdapter : ListAdapter<Member, MemberAdapter.MemberViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface AdapterItemClickListener{
        fun onClick(position: Int)
    }

    lateinit var adapterItemClickListener: AdapterItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_list_item, parent, false)
        return MemberViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MemberViewHolder(private val binding: ViewDataBinding ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener{
                adapterItemClickListener.onClick(adapterPosition)
            }
        }

        fun bind(member: Member) {
            binding.setVariable(BR.obj, member)
            binding.executePendingBindings()
        }

    }

    fun getItemAt(position: Int): Member = getItem(position)

}

