package com.soft.attendancekt.model.entity

import androidx.databinding.BaseObservable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = "",
    var age: Int = 0,
    val gender: Gender = Gender.MALE,
    var phone: String = "",
    var email: String = "",
    var photo: String = "",
    var barcode: String = ""
) : BaseObservable(){

    override fun toString(): String {
        return name
    }
}

enum class Gender{
    MALE, FEMALE
}