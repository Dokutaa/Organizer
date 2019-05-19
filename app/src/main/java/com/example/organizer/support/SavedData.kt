package com.example.organizer.support

import com.chibatching.kotpref.KotprefModel

object SavedData : KotprefModel() {
    var isLoggedIn by booleanPref(default = false)
    val currentUserName by stringPref(default = "Dokutaa")
    val currentUserLogin by stringPref(default = "1111")
    val currentUserPassword by stringPref(default = "2222")
    val currentUserImg by stringPref(default = "https://www.kulina.ru/images/docs/Image/zes(6).jpg")
}