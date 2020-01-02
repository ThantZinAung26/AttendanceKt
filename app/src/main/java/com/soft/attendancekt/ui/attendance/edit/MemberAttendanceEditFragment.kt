package com.soft.attendancekt.ui.attendance.edit

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.soft.attendancekt.R
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.ui.MemberAdapter
import com.soft.attendancekt.ui.member.FragmentAddMember
import kotlinx.android.synthetic.main.fragment_attendance_edit.*
import kotlinx.android.synthetic.main.layout_dialog.*
import java.util.*
import kotlin.collections.ArrayList

class MemberAttendanceEditFragment : Fragment() {

    lateinit var viewModel: MemberAttendanceEditViewModel
    lateinit var adapter: ArrayAdapter<Member>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MemberAttendanceEditViewModel::class.java]
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)

        viewModel.members.observe(this, androidx.lifecycle.Observer {
            adapter.addAll(it)
        })

        viewModel.attendance.observe(this, androidx.lifecycle.Observer {
            viewModel.attendanceId.value
        })
        val id = arguments?.getLong(FragmentAddMember.KEY_MEMBER_ID) ?: 0
        viewModel.attendanceId.value = id

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
            val list = arrayListOf<String>(
                    "Min Khant Kyaw",
                    "Thant Sin Aung",
                    "Thein mwe naing",
                    "Khin Zar li"
                )

            val dialog = AlertDialog.Builder(this.activity)
            dialog.setTitle("Select Member")
            dialog.setAdapter(adapter) { di, i ->
                adapter.getItem(i)?.also {
                    viewModel.memberId.value = it.id
                    viewModel.attendance.value?.memberId = it.id
                }
                di.dismiss()
            }
            dialog.create()
            dialog.show()
        }
    }


}