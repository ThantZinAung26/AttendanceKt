package com.soft.attendancekt.util

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface CudDao<E> {

    @Insert
    fun insert(e: E)

    @Update
    fun update(e: E)

    @Delete
    fun delete(e: E)
}