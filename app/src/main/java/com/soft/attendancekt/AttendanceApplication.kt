package com.soft.attendancekt

import android.app.Application

class AttendanceApplication : Application() {
    companion object {
        lateinit var currentUser: String
    }
}