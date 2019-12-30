package com.soft.attendancekt.ui.attendance.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soft.attendancekt.R
import kotlinx.android.synthetic.main.fragment_attendance_edit.*

class MemberAttendanceEditFragment : Fragment() {

    lateinit var viewModel: MemberAttendanceEditViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        /*spinner.setDro{
            viewModel.members.value
        }*/
        memberInput.setOnClickListener {

        }

    }

}