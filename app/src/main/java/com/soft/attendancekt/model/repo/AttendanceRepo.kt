package com.soft.attendancekt.model.repo

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.soft.attendancekt.model.dao.AttendanceDao
import com.soft.attendancekt.model.entity.Attendance
import com.soft.attendancekt.model.entity.tuple.MemberAttendance

class AttendanceRepo(private val dao: AttendanceDao) {
    fun save(attendance: Attendance) {
        if (attendance.id > 0) {
            dao.update(attendance)
        } else {
            dao.insert(attendance)
        }
    }

    fun getAttendance(id: Long) = dao.getAllAttendance(id)

    fun getAll(): LiveData<PagedList<MemberAttendance>> =
        LivePagedListBuilder(dao.getAll(), 3).build()

    fun deleteAttendance(attendance: Attendance) = dao.delete(attendance)
}
