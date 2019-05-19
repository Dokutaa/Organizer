package com.example.organizer.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import com.example.organizer.R
import com.example.organizer.support.SavedData
import com.example.organizer.adapters.SmallAdapter
import com.example.organizer.calendar.initCalendar
import com.example.organizer.database.ActivitiesDatabase
import com.example.organizer.database.DailyActivity
import kotlinx.coroutines.*
import kotlinx.serialization.ImplicitReflectionSerializer
import org.jetbrains.anko.startActivity
import ru.cleverpumpkin.calendar.CalendarView

class MainActivity : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var calendarView: CalendarView
    private lateinit var smallAdapter: SmallAdapter
    private lateinit var activitiesList: MutableList<DailyActivity>
    private lateinit var dayMonthYear: String
    private var db: ActivitiesDatabase? = null

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = ActivitiesDatabase.getInstance(this)

        calendarView = findViewById(R.id.calendar_view)
        activitiesList = mutableListOf()
        dayMonthYear = calendarView.selectedDate.toString()

        initCalendar(calendarView)
        initRecyclerView(this)
        loadDailyActivities(dayMonthYear)

        calendarView.onDateClickListener = {
            dayMonthYear = calendarView.selectedDate.toString()
            loadDailyActivities(dayMonthYear)
        }

        calendarView.onDateLongClickListener = {
            showPopup(calendarView)
        }

    }

    @ImplicitReflectionSerializer
    private fun showPopup(view: View) {
        var popup: PopupMenu?
        popup = PopupMenu(this@MainActivity, view)
        popup.inflate(R.menu.calendar_item_popup_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {item: MenuItem? ->
            when (item!!.itemId) {
                R.id.add_new_activity -> {
                    launchCreateNewActivity()
                }
                R.id.clear_all_activities -> {
                    dayMonthYear = calendarView.selectedDate.toString()
                    clearDailyActivities()
                    loadDailyActivities(dayMonthYear)
                }
            }

            true
        })
        popup.show()
    }

    @ImplicitReflectionSerializer
    private fun launchCreateNewActivity() = GlobalScope.launch(Dispatchers.Main) {
        dayMonthYear = calendarView.selectedDate.toString()
        startActivity<CreateNewActivity>("dayMonthYear" to dayMonthYear)
    }

    // TODO Understand why small recycler shows previous date instead of a selected one

    private fun getActivities(day: String): MutableList<DailyActivity> {
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
                activitiesList = db!!.activityDao().selectByDate(
                    user = SavedData.currentUserName,
                    dayMonthYear = day
                )
            }.await()
        }
        return activitiesList
    }

    @ImplicitReflectionSerializer
    private fun initRecyclerView(context: Context) {
        recycler = findViewById(R.id.small_activities_recycler_view)
        recycler.layoutManager = LinearLayoutManager(context)
        smallAdapter = SmallAdapter()
        recycler.adapter = smallAdapter
    }

    private fun clearDailyActivities() {
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
                db!!.activityDao().deleteDailyActivites(
                    user = SavedData.currentUserName,
                    dayMonthYear = dayMonthYear
                )
            }.await()
        }
    }

    private fun loadDailyActivities(day: String) {
        val activities = getActivities(day)
        smallAdapter.clearItems()
        smallAdapter.setItems(activities)
    }

}
