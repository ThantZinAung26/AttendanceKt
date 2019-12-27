package com.soft.attendancekt.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.util.CudDao

@Dao
interface MemberDao: CudDao<Member> {

    @Query("SELECT * FROM member WHERE id = :id LIMIT 1")
    fun getMember(id: Int): LiveData<Member>

    @Query("SELECT * FROM member")
    fun getAll(): LiveData<List<Member>>

}