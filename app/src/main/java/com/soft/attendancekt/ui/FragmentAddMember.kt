package com.soft.attendancekt.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.soft.attendancekt.R
import com.soft.attendancekt.databinding.MemberBinding
import kotlinx.android.synthetic.main.fragment_list_item.*
import kotlinx.android.synthetic.main.layout_add_member.*

class FragmentAddMember : Fragment() {

    lateinit var memberViewModel: MemberViewModel
    lateinit var memberBinding: MemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        memberBinding = MemberBinding.inflate(inflater, container, false)
        memberBinding.lifecycleOwner = this
        memberBinding.viewModel = memberViewModel
        return memberBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        save.setOnClickListener {
            memberViewModel.save()
            Log.d("TEST","Right Click")
        }
    }
}