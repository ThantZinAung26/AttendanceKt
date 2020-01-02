package com.soft.attendancekt.ui.attendance.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.soft.attendancekt.ServiceLocator
import com.soft.attendancekt.model.entity.Attendance
import com.soft.attendancekt.model.entity.Member

class MemberAttendanceEditViewModel(application: Application) : AndroidViewModel(application) {

    private val attendanceRepo = ServiceLocator.getInstance(application).attendanceRepo
    private val memberRepo = ServiceLocator.getInstance(application).memberRepo

    val memberId = MutableLiveData<Int>()

    val attendanceId = MutableLiveData<Long>()

    val attendance: LiveData<Attendance> = Transformations.switchMap(attendanceId) {
        if (it > 0) {
            Log.e("TAG", "Old")
            attendanceRepo.getAttendance(it)
        } else {
            Log.e("TAG", "New")
            val liveData = MutableLiveData<Attendance>()
            liveData.value = Attendance()
            liveData
        }
    }

    var member: LiveData<Member> = Transformations.switchMap(memberId) {
        memberRepo.getMember(it)
    }

    val members: LiveData<List<Member>> by lazy { memberRepo.getAll() }

    fun save() {
        attendance.value?.also { attendanceRepo.save(it) }
    }



}