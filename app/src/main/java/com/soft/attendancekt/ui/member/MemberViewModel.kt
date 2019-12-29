package com.soft.attendancekt.ui.member

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soft.attendancekt.ServiceLocator
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.model.repo.MemberRepo

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    var memberRepo: MemberRepo = ServiceLocator.getInstance(application).memberRepo()

    var member: MutableLiveData<Member> = MutableLiveData()

    var positionMember: LiveData<Member>? = null

    //null pointer
    fun save() {
        //memberRepo.save(member.value!!)
        member.value?.also { memberRepo.save(it) }
    }

    fun delete() {
        member.value?.also { memberRepo.deleteMember(it) }
    }

    fun init(id: Int) {
        if (id > 0) {
            memberRepo.getMember(id)
        } else {
            member.value = Member()
        }
    }

}