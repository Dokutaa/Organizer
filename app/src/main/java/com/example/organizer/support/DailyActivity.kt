package com.example.organizer.support

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
class DailyActivity(
    val user: User,
    val id: Long,
    val dayMonthYear: String,
    val time: String,
    val activity: String,
    val address: String = "",
    val notes: String = "",
    val img: String = ""
)
