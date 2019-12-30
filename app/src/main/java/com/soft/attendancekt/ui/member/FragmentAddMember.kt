package com.soft.attendancekt.ui.member

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.soft.attendancekt.MainActivity
import com.soft.attendancekt.databinding.MemberBinding
import kotlinx.android.synthetic.main.layout_add_member.*

class FragmentAddMember : Fragment() {

    lateinit var memberViewModel: MemberViewModel
    lateinit var memberBinding: MemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memberViewModel = ViewModelProviders.of(this).get(MemberViewModel::class.java)

        val id = arguments?.getInt(KEY_MEMBER_ID) ?: 0

        memberViewModel.member.observe(this, Observer {
            memberViewModel.member
        })
        memberViewModel.memberId.value = id
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
            //Log.d("SAVE","save value")
            findNavController().navigateUp()
        }

        delete.setOnClickListener {
            memberViewModel.delete()
        }
    }

    companion object {
        const val KEY_MEMBER_ID = "member_id"
    }

    override fun onDestroy() {
        super.onDestroy()
        val activity: MainActivity = requireActivity() as MainActivity
        activity.hideKeyboard()
    }

}