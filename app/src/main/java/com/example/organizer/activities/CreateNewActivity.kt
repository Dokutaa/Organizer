package com.example.organizer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.organizer.R
import com.example.organizer.support.DailyActivity
import com.example.organizer.support.User
import kotlinx.coroutines.*
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.coroutines.CoroutineContext

class CreateNewActivity : AppCompatActivity() {

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

    private var id = 1
    private val user = User(
        id = 19283474L,
        name = "Dokutaa",
        login = "login",
        password = "password",
        userImg = "https://bipbap.ru/wp-content/uploads/2017/12/65620375-6b2b57fa5c7189ba4e3841d592bd5fc1-800-640x426.jpg"
    )

    @ImplicitReflectionSerializer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new)

        initViews()
        showDate()
        addActivityBtn.onClick {
            launchFullListActivity(createActivity())
        }
    }

    @ImplicitReflectionSerializer
    private fun launchFullListActivity(activity: DailyActivity) = GlobalScope.launch(Dispatchers.Main) {
        val newActivityJson = Json.stringify(activity)
        withContext(Dispatchers.IO) {
            startActivity<FullListActivity>("newActivityJson" to newActivityJson)
        }
    }

    @ImplicitReflectionSerializer
    private fun showDate() {
        val dateJson = intent.getStringExtra("dateJson")
        val date: String = Json.parse(dateJson)
        dateTextView.text = date
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
        val timeCaptured = timeEditText.text.toString()
        val activityCaptured = activityEditText.text.toString()
        val streetCaptured = streetEditText.text.toString()
        val houseCaptured = houseEditText.text.toString()
        val aptCaptured = aptEditText.text.toString()
        val cityCaptured = cityEditText.text.toString()
        val notesCaptured = notesEditText.text.toString()

        val address = "$cityCaptured, $streetCaptured, $houseCaptured, Apt. No $aptCaptured"

        val dayMonthYear = dateTextView.text.toString()

        newActivity = DailyActivity(
            user = user,
            id = id.toLong(),
            dayMonthYear = dayMonthYear,
            time = timeCaptured,
            activity = activityCaptured,
            address = address,
            notes = notesCaptured,
            img = "https://www.kulina.ru/images/docs/Image/zes(6).jpg"
        )

        id += 1

        return newActivity
    }
}
