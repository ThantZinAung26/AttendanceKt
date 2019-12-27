package com.soft.attendancekt.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.soft.attendancekt.ServiceLocator
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.model.repo.MemberRepo

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    var memberRepo: MemberRepo = ServiceLocator.getInstance(application).memberRepo()

    var member: MutableLiveData<Member> = MutableLiveData()

    fun save() {
        memberRepo.save(member.value!!)
    }

}