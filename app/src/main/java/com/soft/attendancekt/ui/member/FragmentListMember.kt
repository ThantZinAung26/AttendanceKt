package com.soft.attendancekt.ui.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.soft.attendancekt.MainActivity
import com.soft.attendancekt.R
import com.soft.attendancekt.ui.MemberAdapter
import kotlinx.android.synthetic.main.fragment_list_item.*

class FragmentListMember : Fragment() {

    lateinit var memberListViewModel: MemberListViewModel
    lateinit var memberAdapter: MemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberAdapter = MemberAdapter()

        memberListViewModel = ViewModelProviders.of(this).get(MemberListViewModel::class.java)

        memberListViewModel.memberListViewModel.observe(this, Observer {
            memberAdapter.submitList(it)
        })

        memberAdapter.adapterItemClickListener = object : MemberAdapter.AdapterItemClickListener {
            override fun onClick(position: Int) {
                val args = Bundle()
                args.putInt(FragmentAddMember.KEY_MEMBER_ID, memberAdapter.getItemAt(position).id)
                findNavController().navigate(R.id.action_fragmentListMember_to_fragmentAddMember, args)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            recyclerView.adapter = memberAdapter
        }

        fab.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentListMember_to_fragmentAddMember)
        }
    }

}