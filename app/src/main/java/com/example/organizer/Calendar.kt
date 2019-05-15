package com.example.organizer

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import ru.cleverpumpkin.calendar.CalendarDate
import ru.cleverpumpkin.calendar.CalendarView
import java.util.*

private lateinit var datesList: MutableList<CalendarDate>
private lateinit var initialDate: CalendarDate

private val date = Date()
private val calendar = Calendar.getInstance()

fun initCalendar(calendarView: CalendarView) {

    datesList = mutableListOf()

    // Initial date set
    calendar.time = date
    initialDate = CalendarDate(calendar.time)
    datesList.add(initialDate)

    // List of initially preselected dates
    val preselectedDates: MutableList<CalendarDate> = datesList

    // Setting up calendar with all the parameters
    calendarView.setupCalendar(
        initialDate = initialDate,
        selectionMode = CalendarView.SelectionMode.SINGLE,
//        selectedDates = preselectedDates,
        firstDayOfWeek = Calendar.MONDAY,
        showYearSelectionView = true
    )

    calendarView
}

