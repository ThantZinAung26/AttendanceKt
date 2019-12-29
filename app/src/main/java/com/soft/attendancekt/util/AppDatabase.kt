package com.soft.attendancekt.util

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.soft.attendancekt.model.dao.AttendanceDao
import com.soft.attendancekt.model.dao.MemberDao
import com.soft.attendancekt.model.entity.Attendance
import com.soft.attendancekt.model.entity.Member
import com.soft.attendancekt.model.entity.Status
import org.joda.time.DateTime
import org.joda.time.LocalDateTime

@Database(
    entities = [Member::class, Attendance::class],
    version = 1, exportSchema = false
)
@TypeConverters(TypeConverterr::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    abstract fun attendanceDao(): AttendanceDao

}


class TypeConverterr {

    companion object {

        @JvmStatic
        @TypeConverter
        fun getLocalDateTime(timeStamp: Long): DateTime {
            return DateTime(timeStamp).toDateTime()
        }

        @JvmStatic
        @TypeConverter
        fun setLocalDateTime(dateTime: DateTime): Long {
            return dateTime.millis
        }

        @JvmStatic
        @TypeConverter
        fun getAttendanceStatus(value: Int): Status = Status.values()[value]

        @JvmStatic
        @TypeConverter
        fun setAttendanceStatus(status: Status): Int = status.ordinal
    }
}