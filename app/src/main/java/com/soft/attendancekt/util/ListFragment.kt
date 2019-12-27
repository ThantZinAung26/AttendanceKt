package com.soft.attendancekt.util

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

abstract class ListFragment : Fragment() {

    abstract fun adapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

}