package com.soft.attendancekt.ui.attendance.edit

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.soft.attendancekt.R
import kotlinx.android.synthetic.main.fragment_attendance_edit.*
import java.util.*

class MemberAttendanceEditFragment : Fragment() {

    lateinit var viewModel: MemberAttendanceEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MemberAttendanceEditViewModel::class.java]

        viewModel.members.observe(this, androidx.lifecycle.Observer {
            viewModel.members
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attendance_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        memberInput.setOnClickListener {
            val dialog = AlertDialog.Builder(this.context)
            dialog.setTitle("Select Member")
            dialog.show()
        }
    }


}