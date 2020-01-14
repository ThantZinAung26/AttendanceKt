package com.soft.attendancekt

import android.content.Context
import androidx.room.Room
import com.soft.attendancekt.model.repo.AttendanceRepo
import com.soft.attendancekt.model.repo.MemberRepo
import com.soft.attendancekt.util.AppDatabase

interface ServiceLocator {

    companion object {
        lateinit var instant: ServiceLocator

        fun getInstance(context: Context) = if (!this::instant.isInitialized) {
            instant = DefaultServiceLocator(context)
            instant
        } else {
            instant
        }

    }

    val memberRepo: MemberRepo

    val attendanceRepo: AttendanceRepo

    class DefaultServiceLocator(val context: Context) : ServiceLocator {
        val database: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "attendance_record").build()

        override val memberRepo: MemberRepo by lazy { MemberRepo(database.memberDao()) }


        override val attendanceRepo by lazy { AttendanceRepo(database.attendanceDao()) }

    }
}



