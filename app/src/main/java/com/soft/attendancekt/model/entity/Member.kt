package com.soft.attendancekt.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
data class Member(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String = "",
    var age: Int = 0,
//    @TypeConverters(TypeConverter::class)
//    val gender: Gender,
    var phone: String = "",
    var email: String = "",
    var photo: String = "",
    var barcode: String = ""
)/*{
    enum class Gender{
        MALE, FEMALE
    }
}*/