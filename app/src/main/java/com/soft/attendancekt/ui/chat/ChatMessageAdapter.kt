package com.soft.attendancekt.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soft.attendancekt.AttendanceApplication
import com.soft.attendancekt.BR
import com.soft.attendancekt.R
import com.soft.attendancekt.model.entity.ChatMessage
import com.soft.attendancekt.model.entity.MessageType

class ChatMessageAdapter : ListAdapter<ChatMessage, ChatMessageAdapter.ChatMessageHiewHolder>(
    DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChatMessage>() {
            override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageHiewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return ChatMessageHiewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatMessageHiewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {

        val  msg = getItem(position)
        return when(msg.messageType) {
            MessageType.JOIN, MessageType.LEAVE -> R.layout.layout_chat_info
            MessageType.CHAT -> {
                if (msg.sender == AttendanceApplication.currentUser) {
                    R.layout.layout_chat_send
                } else {
                    R.layout.layout_chat_receive
                }
            }
        }
    }


    inner class ChatMessageHiewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(msg: ChatMessage) {
            binding.setVariable(BR.msg, msg)
        }

    }


}