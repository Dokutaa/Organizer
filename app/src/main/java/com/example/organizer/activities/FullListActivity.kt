package com.example.organizer.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.organizer.R
import com.example.organizer.adapters.FullAdapter
import com.example.organizer.database.ActivitiesDatabase
import com.example.organizer.database.DailyActivity
import com.example.organizer.support.SavedData
import kotlinx.coroutines.*
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import org.jetbrains.anko.startActivity

@ImplicitReflectionSerializer
class FullListActivity : AppCompatActivity() {

    private var db: ActivitiesDatabase? = null

    lateinit var activitiesList: MutableList<DailyActivity>
    lateinit var dateTextView: TextView
    lateinit var addNewBtn: Button
    lateinit var recycler: RecyclerView
    private lateinit var dayMonthYear: String
    private lateinit var returnToMainBtn: ImageButton
    private lateinit var fullAdapter: FullAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_list)

        db = ActivitiesDatabase.getInstance(this)

        dayMonthYear = ""

        initRecyclerView(this)
        loadDailyActivities()
        setBtnListener()
        setDate(dayMonthYear)
    }

    private fun setDate(dateStr: String) {
        dateTextView = findViewById(R.id.full_list_date_text_view)
        dateTextView.text = dateStr
    }

    private fun setBtnListener() {
        returnToMainBtn = findViewById(R.id.return_button)
        addNewBtn = findViewById(R.id.full_list_add_activity_button)
        addNewBtn.setOnClickListener {
            val date = dateTextView.text.toString()
            startActivity<CreateNewActivity>("dayMonthYear" to date)
        }
        returnToMainBtn.setOnClickListener {
            startActivity<MainActivity>("dayMonthYear" to dayMonthYear)
        }
    }

    private fun getActivities(): MutableList<DailyActivity> {
        activitiesList = mutableListOf()
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
//                val activitiesListJson = intent.getStringExtra("activitiesListJson")
//                activitiesList = Json.parse(activitiesListJson)
//                dayMonthYear = activitiesList.first().dayMonthYear
                activitiesList = db!!.activityDao().selectByDate(
                    user = SavedData.currentUserName,
                    dayMonthYear = dayMonthYear
                )
            }.await()
        }
        return activitiesList
    }

    private fun initRecyclerView(context: Context) {
        recycler = findViewById(R.id.full_list_recycler_view)
        recycler.layoutManager = LinearLayoutManager(context)
        fullAdapter = FullAdapter()
        recycler.adapter = fullAdapter
    }

    @ImplicitReflectionSerializer
    private fun loadDailyActivities() {
        val activities = getActivities()
        fullAdapter.clearItems()
        fullAdapter.setItems(activities)
    }

}
