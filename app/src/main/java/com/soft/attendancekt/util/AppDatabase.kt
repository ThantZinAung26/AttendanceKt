package com.soft.attendancekt.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soft.attendancekt.model.dao.MemberDao
import com.soft.attendancekt.model.entity.Member

@Database(entities = [Member::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

}