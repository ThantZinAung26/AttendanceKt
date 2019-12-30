package com.soft.attendancekt.ui.member

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.soft.attendancekt.ServiceLocator
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.model.repo.MemberRepo

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    var memberRepo: MemberRepo = ServiceLocator.getInstance(application).memberRepo

    var memberId: MutableLiveData<Int> = MutableLiveData()

    var member: LiveData<Member> = Transformations.switchMap(memberId) {

        if (it > 0) {
            memberRepo.getMember(it)
        } else {
            val liveData = MutableLiveData<Member>()
            liveData.value = Member()
            liveData
        }
    }

    //null pointer
    fun save() {
        //memberRepo.save(member.value!!)
        member.value?.also {
            memberRepo.save(it)
        }
    }

    fun delete() {
        member.value?.also { memberRepo.deleteMember(it) }
    }

}