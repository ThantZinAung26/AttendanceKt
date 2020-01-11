package com.soft.attendancekt.ui.attendance.edit

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.soft.attendancekt.MainActivity
import com.soft.attendancekt.R
import com.soft.attendancekt.databinding.AttendanceEditBinding
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.model.entity.Status
import com.soft.attendancekt.ui.MemberAdapter
import com.soft.attendancekt.ui.member.FragmentAddMember
import kotlinx.android.synthetic.main.fragment_attendance_edit.*
import kotlinx.android.synthetic.main.fragment_attendance_edit.view.*
import kotlinx.android.synthetic.main.layout_dialog.*
import java.util.*
import kotlin.collections.ArrayList

class MemberAttendanceEditFragment : Fragment() {

    lateinit var viewModel: MemberAttendanceEditViewModel
    lateinit var adapter: ArrayAdapter<Member>
    lateinit var binding: AttendanceEditBinding
    lateinit var activity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MemberAttendanceEditViewModel::class.java]
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)

        viewModel.members.observe(this, androidx.lifecycle.Observer {
            adapter.addAll(it)
        })

        viewModel.attendance.observe(this, androidx.lifecycle.Observer {
            viewModel.memberId.value = it.memberId
        })
        val id = arguments?.getLong(FragmentAddMember.KEY_MEMBER_ID) ?: 0
        viewModel.attendanceId.value = id

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AttendanceEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity = requireActivity() as MainActivity
        activity.hideKeyboard()
        memberInput.setOnClickListener {
            val dialog = AlertDialog.Builder(this.activity)
            dialog.setTitle("Select Member")
            dialog.setAdapter(adapter) { di, i ->
                adapter.getItem(i)?.also {
                    viewModel.memberId.value = it.id
                    viewModel.attendance.value?.memberId = it.id
                }
                activity.hideKeyboard()

                di.dismiss()
            }
            dialog.create()
            dialog.show()

            /*radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val radioButton = view.findViewById<RadioButton>(checkedId)
                viewModel.attendance.value?.status.apply { radioButton?.text }
            }*/
        }

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                onCustomStatus(group, checkedId)
            }
        })

        save.setOnClickListener {
            viewModel.save()
            findNavController().navigateUp()
        }

        delete.setOnClickListener {
            viewModel.delete()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity.hideKeyboard()
    }

    //status radio
    fun onCustomStatus(radioGroup: RadioGroup, id: Int) {
        //val radio: RadioButton
        //viewModel.attendance.value?.status = Status.PRESENT
        when (id) {
            R.id.rbPresent -> viewModel.attendance.value?.status =
                Status.PRESENT
            R.id.rbAbsent -> viewModel.attendance.value?.status =
                Status.ABSENT
        }
    }
}