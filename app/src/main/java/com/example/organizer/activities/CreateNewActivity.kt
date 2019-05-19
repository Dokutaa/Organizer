package com.example.organizer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.organizer.R
import com.example.organizer.database.ActivitiesDatabase
import com.example.organizer.database.DailyActivity
import kotlinx.coroutines.*
import kotlinx.serialization.ImplicitReflectionSerializer
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

@ImplicitReflectionSerializer
class CreateNewActivity : AppCompatActivity() {

    private var db: ActivitiesDatabase? = null

    lateinit var dateTextView: TextView
    lateinit var timeEditText: EditText
    lateinit var activityEditText: EditText
    lateinit var streetEditText: EditText
    lateinit var houseEditText: EditText
    lateinit var aptEditText: EditText
    lateinit var cityEditText: EditText
    lateinit var notesEditText: EditText
    lateinit var imgTextView: TextView
    lateinit var addActivityBtn: Button

    private lateinit var newActivity: DailyActivity
    private lateinit var dayMonthYear: String
    private lateinit var activitiesList: MutableList<DailyActivity>

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new)

        db = ActivitiesDatabase.getInstance(this)

        dayMonthYear = intent.getStringExtra("dayMonthYear")

        initViews()
        showDate()

        addActivityBtn.onClick {
            createActivity()
            launchFullListActivity()
        }
    }

    fun launchFullListActivity() {
        startActivity<FullListActivity>("dayMonthYear" to dayMonthYear)
    }

    private fun showDate() {
        dateTextView.text = dayMonthYear
    }

    private fun initViews() {
        dateTextView = findViewById(R.id.add_activity_dialog_date)
        timeEditText = findViewById(R.id.add_time_edit_text)
        activityEditText = findViewById(R.id.add_activity_edit_text)
        streetEditText = findViewById(R.id.add_street_edit_text)
        houseEditText = findViewById(R.id.add_house_edit_text)
        aptEditText = findViewById(R.id.add_apartment_edit_text)
        cityEditText = findViewById(R.id.add_apartment_edit_text)
        notesEditText = findViewById(R.id.add_notes_edit_text)
        imgTextView = findViewById(R.id.add_image_url)
        addActivityBtn = findViewById(R.id.add_activity_button)
    }

    private fun createActivity(): DailyActivity {

        newActivity = DailyActivity()

        GlobalScope.launch(Dispatchers.Main) {

            val timeCaptured = timeEditText.text.toString()
            val activityCaptured = activityEditText.text.toString()
            val streetCaptured = streetEditText.text.toString()
            val houseCaptured = houseEditText.text.toString()
            val aptCaptured = aptEditText.text.toString()
            val cityCaptured = cityEditText.text.toString()
            val notesCaptured = notesEditText.text.toString()
            val address = "$cityCaptured, $streetCaptured, $houseCaptured, Apt. No $aptCaptured"
            val date = dateTextView.text.toString()


            newActivity.dayMonthYear = date
            newActivity.time = timeCaptured
            newActivity.activity = activityCaptured
            newActivity.address = address
            newActivity.notes = notesCaptured
            newActivity.img = "https://www.kulina.ru/images/docs/Image/zes(6).jpg"

            async(Dispatchers.IO) {
                db!!.activityDao().insertActivity(newActivity)
            }.await()
        }

        return newActivity
    }
}
