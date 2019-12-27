package com.soft.attendancekt

import android.content.Context
import androidx.room.Room
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

    fun memberRepo(): MemberRepo

    class DefaultServiceLocator(val context: Context) : ServiceLocator {
        val database: AppDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries().build()


        override fun memberRepo(): MemberRepo = MemberRepo(database.memberDao())

    }
}



