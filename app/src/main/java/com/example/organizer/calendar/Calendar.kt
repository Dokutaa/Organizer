package com.example.organizer.calendar

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

    val preselectedDates: List<CalendarDate> = listOf(initialDate)

    // Setting up calendar with all the parameters
    calendarView.setupCalendar(
        initialDate = initialDate,
        selectionMode = CalendarView.SelectionMode.SINGLE,
        selectedDates = preselectedDates,
        firstDayOfWeek = Calendar.MONDAY,
        showYearSelectionView = true
    )

    calendarView
}

