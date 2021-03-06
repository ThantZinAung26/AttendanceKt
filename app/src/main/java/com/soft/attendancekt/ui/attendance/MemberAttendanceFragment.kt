package com.soft.attendancekt.ui.attendance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.soft.attendancekt.R
import com.soft.attendancekt.ui.MemberAdapter
import com.soft.attendancekt.ui.attendance.edit.MemberAttendanceEditFragment
import kotlinx.android.synthetic.main.fragment_member_attendance.*

class MemberAttendanceFragment : Fragment() {

    private lateinit var viewModel: MemberAttendanceViewModel

    private lateinit var adapter: MemberAttendanceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MemberAttendanceAdapter()
        viewModel = ViewModelProviders.of(this)[MemberAttendanceViewModel::class.java]
        viewModel.attendances.observe(this, Observer {
            adapter.submitList(it)
        })

        adapter.adapterItemClickListener = object : MemberAttendanceAdapter.AdapterItemClickListener {
            override fun onClick(position: Int) {
                val args = Bundle()
                adapter.getItemAt(position)?.id?.let {
                    args.putLong(MemberAttendanceEditFragment.MT_ID, it)
                }
                findNavController().navigate(R.id.action_memberAttendanceFragment_to_memberAttendanceEditFragment)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_member_attendance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL))
            adapter = this@MemberAttendanceFragment.adapter
        }

        fab.setOnClickListener{
            findNavController().navigate(R.id.action_memberAttendanceFragment_to_memberAttendanceEditFragment)
        }
    }

}