package com.soft.attendancekt.model.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.soft.attendancekt.model.entity.Attendance
import com.soft.attendancekt.model.entity.tuple.MemberAttendance
import com.soft.attendancekt.util.CudDao

@Dao
interface AttendanceDao : CudDao<Attendance> {

    @Query("SELECT * FROM Attendance WHERE id = :id LIMIT 1")
    fun getAllAttendance(id: Long): LiveData<Attendance>

    @Query("SELECT a.id, m.name, a.event_time, a.status FROM Attendance a " +
            "LEFT JOIN MEMBER m ON a.member_id = m.id " +
            "ORDER BY a.event_time DESC ")
    fun getAll(): DataSource.Factory<Int, MemberAttendance>
}