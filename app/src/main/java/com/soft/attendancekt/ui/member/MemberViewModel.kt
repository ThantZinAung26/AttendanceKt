package com.soft.attendancekt.ui.member

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.soft.attendancekt.ServiceLocator
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.model.repo.MemberRepo
import com.soft.attendancekt.util.AppExecutor
import java.lang.Exception

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    var memberRepo: MemberRepo = ServiceLocator.getInstance(application).memberRepo

    var memberId: MutableLiveData<Int> = MutableLiveData()

    var member: LiveData<Member> = Transformations.switchMap(memberId) {

        if (it > 0) {
            Log.e("TAG", "Old")
            memberRepo.getMember(it)
        } else {
            Log.e("TAG", "New")
            val liveData = MutableLiveData<Member>()
            liveData.value = Member()
            liveData
        }
    }

    //null pointer
    fun save() {
        //memberRepo.save(member.value!!)
        AppExecutor.io()?.execute {
            try {
                member.value?.also {
                    memberRepo.save(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun delete() {

        AppExecutor.io()?.execute {
            try {
                member.value?.also { memberRepo.deleteMember(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}