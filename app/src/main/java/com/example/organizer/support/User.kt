package com.example.organizer.support

import kotlinx.serialization.Serializable

@Serializable
class User(
    val id: Long,
    val name: String,
    val login: String,
    val password: String,
    val userImg: String
)