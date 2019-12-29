package com.soft.attendancekt.ui.member

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.soft.attendancekt.ServiceLocator
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.model.repo.MemberRepo

class MemberListViewModel(application: Application) : AndroidViewModel(application) {
    var memberRepo: MemberRepo = ServiceLocator.getInstance(application).memberRepo()

    var memberListViewModel: LiveData<List<Member>> = memberRepo.getAll()

    fun getMembers(): LiveData<List<Member>> = memberListViewModel
}