package com.soft.attendancekt.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.soft.attendancekt.R
import kotlinx.android.synthetic.main.fragment_login.*

class FragmentLogin : Fragment() {

    private lateinit var viewModel: ChatMessageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity())[ChatMessageViewModel::class.java]
        /*viewModel.connectResult.observe(requireActivity(), Observer {
            if (it) {
                findNavController().navigate(R.id.action_fragmentLogin_to_fragmentChatting)
            }
        })*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnJoin.setOnClickListener {
            edUserName.text.toString().also {
                progressBar.visibility = View.VISIBLE
                viewModel.connect(it)
            }
            viewModel.connectResult.observe(requireActivity(), Observer {
                if (it) {
                    findNavController().navigate(R.id.action_fragmentLogin_to_fragmentChatting)
                }
            })
        }

    }

    /*override fun onDestroyView() {
        super.onDestroyView()
        viewModel.disconnect()
        viewModel.connectResult.removeObserver {
            findNavController().navigate(R.id.action_fragmentLogin2_to_fragmentChatting)
        }
    }*/

}