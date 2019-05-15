package com.example.organizer.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import com.example.organizer.R
import com.example.organizer.adapters.FullAdapter
import com.example.organizer.support.DailyActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import org.jetbrains.anko.startActivity

class FullListActivity : AppCompatActivity() {

    lateinit var activitiesList: MutableList<DailyActivity>
    lateinit var dateTextView: TextView
    lateinit var addNewBtn: Button
    lateinit var recycler: RecyclerView
    private lateinit var fullAdapter: FullAdapter

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_list)

        activitiesList = mutableListOf()
        initRecyclerView(this)
        loadDailyActivities()
        setDate(activitiesList[0].dayMonthYear)
        setAddBtnListener()

    }

    private fun setDate(dateStr: String) {
        dateTextView = findViewById(R.id.full_list_date_text_view)
        dateTextView.text = dateStr
    }

    @ImplicitReflectionSerializer
    private fun setAddBtnListener() {
        addNewBtn = findViewById(R.id.full_list_add_activity_button)
        addNewBtn.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val date = dateTextView.text.toString()
                val dateJson = Json.stringify(date)
                withContext(Dispatchers.IO) {
                    startActivity<CreateNewActivity>("dateJson" to dateJson)
                }
            }
        }
    }

    @ImplicitReflectionSerializer
    private fun getActivities(): MutableList<DailyActivity> {

        val newActivityJson = intent.getStringExtra("newActivityJson")
        val newActivity: DailyActivity = Json.parse(newActivityJson)
        activitiesList.add(newActivity)

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
        var activities = getActivities()
        fullAdapter.clearItems()
        fullAdapter.setItems(activities)
    }

//    private fun testUser(): User {
//        return User(
//            id = 19283474L,
//            name = "Dokutaa",
//            login = "login",
//            password = "password",
//            userImg = "https://bipbap.ru/wp-content/uploads/2017/12/65620375-6b2b57fa5c7189ba4e3841d592bd5fc1-800-640x426.jpg"
//        )
//    }

}
