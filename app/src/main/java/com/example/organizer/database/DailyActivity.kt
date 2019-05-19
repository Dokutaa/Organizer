package com.example.organizer.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.organizer.support.SavedData
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "activities")
class DailyActivity(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "user") var user: String = SavedData.currentUserName,
    @ColumnInfo(name = "dayMonthYear") var dayMonthYear: String = "",
    @ColumnInfo(name = "time") var time: String = "",
    @ColumnInfo(name = "activity") var activity: String = "",
    @ColumnInfo(name = "address") var address: String = "",
    @ColumnInfo(name = "notes") var notes: String = "",
    @ColumnInfo(name = "img") var img: String = ""
)
