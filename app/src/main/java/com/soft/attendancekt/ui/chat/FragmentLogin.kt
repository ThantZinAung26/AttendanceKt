package com.soft.attendancekt.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soft.attendancekt.R
import kotlinx.android.synthetic.main.fragment_login.*

class FragmentLogin : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnJoin.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentLogin2_to_fragmentChatting)
        }

    }

}