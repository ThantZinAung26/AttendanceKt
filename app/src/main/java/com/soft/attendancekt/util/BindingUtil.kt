
package com.soft.attendancekt.util

import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.soft.attendancekt.R
import com.soft.attendancekt.model.entity.ChatMessage
import com.soft.attendancekt.model.entity.MessageType

class BindingUtil {

    companion object {

        @JvmStatic
        @BindingAdapter("android:text")
        fun setNumber(text: EditText, value: Int) {
            text.setText(value.toString())
        }

        @JvmStatic
        @InverseBindingAdapter(attribute = "android:text")
        fun getNumber(editText: EditText): Int {
            val value = editText.text.toString()
            return if (value.isNotEmpty()) value.toInt() else 0
        }

        @JvmStatic
        @BindingAdapter("android:text")
        fun setMessage(textView: TextView, msg: ChatMessage?) {
            msg?.also {
                when(it.messageType) {
                    MessageType.JOIN -> textView.text = textView.context.getString(R.string.chat_info_join, it.sender)
                    MessageType.LEAVE -> textView.text = textView.context.getString(R.string.chat_info_leave, it.sender)
                    else -> {}
                }
            }
        }
    }
}
