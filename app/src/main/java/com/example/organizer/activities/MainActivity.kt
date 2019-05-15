package com.example.organizer.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.PopupMenu
import com.example.organizer.R
import com.example.organizer.adapters.SmallAdapter
import com.example.organizer.initCalendar
import com.example.organizer.support.DailyActivity
import com.example.organizer.support.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import org.jetbrains.anko.startActivity
import ru.cleverpumpkin.calendar.CalendarView

class MainActivity : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var calendarView: CalendarView
    private lateinit var smallAdapter: SmallAdapter

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarView = findViewById(R.id.calendar_view)
        initCalendar(calendarView)

        initRecyclerView(this)
        loadDailyActivities()

        calendarView.onDateClickListener = {
            launchCreateNewActivity()
        }

        calendarView.onDateLongClickListener = {
            showPopup(calendarView)
        }

    }

    @ImplicitReflectionSerializer
    private fun launchCreateNewActivity() = GlobalScope.launch(Dispatchers.Main) {
        val date = calendarView.selectedDate.toString()
        val dateJson = Json.stringify(date)
            withContext(Dispatchers.IO) {
                startActivity<CreateNewActivity>("dateJson" to dateJson)
            }
    }

    private fun showPopup(view: View) {
        var popup: PopupMenu?
        popup = PopupMenu(this@MainActivity, view)
        popup.inflate(R.menu.calendar_item_popup_menu)
        popup.show()
    }

    private fun getActivities(): MutableList<DailyActivity> {

        return mutableListOf()

    }

    private fun initRecyclerView(context: Context) {
        recycler = findViewById(R.id.small_activities_recycler_view)
        recycler.layoutManager = LinearLayoutManager(context)
        smallAdapter = SmallAdapter()
        recycler.adapter = smallAdapter
    }

    private fun loadDailyActivities() {
        var activities = getActivities()
        smallAdapter.clearItems()
        smallAdapter.setItems(activities)
    }

    private fun testUser(): User {
        return User(
            id = 19283474L,
            name = "Dokutaa",
            login = "login",
            password = "password",
            userImg = "https://bipbap.ru/wp-content/uploads/2017/12/65620375-6b2b57fa5c7189ba4e3841d592bd5fc1-800-640x426.jpg"
        )
    }

}
