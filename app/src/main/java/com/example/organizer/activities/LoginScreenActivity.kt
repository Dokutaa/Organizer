package com.example.organizer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import com.chibatching.kotpref.Kotpref
import com.example.organizer.R
import com.example.organizer.support.SavedData
import com.example.organizer.support.loadImage
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var loginScreenImageView: ImageView
    private lateinit var loginTextView: EditText
    private lateinit var passwordTextView: EditText
    private lateinit var rememberMeCheckBox: CheckBox
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Kotpref.init(this)
        setContentView(R.layout.activity_login_screen)

        if (SavedData.isLoggedIn) {
            startActivity<MainActivity>()
        }

        initViews()
        loadImage(SavedData.currentUserImg, loginScreenImageView)

        loginButton.onClick {

            var enteredLogin = loginTextView.text.toString()
            var enteredPassword = passwordTextView.text.toString()

            if (enteredLogin == SavedData.currentUserLogin && enteredPassword == SavedData.currentUserPassword) {
                if (rememberMeCheckBox.isChecked) {
                    SavedData.isLoggedIn = true
                }
                startActivity<MainActivity>()
            } else {
                alert("Wrong login or password").show()
            }
        }
    }

    private fun initViews() {
        loginScreenImageView = findViewById(R.id.login_screen_image_view)
        loginTextView = findViewById(R.id.login_edit_text)
        passwordTextView = findViewById(R.id.password_edit_text)
        rememberMeCheckBox = findViewById(R.id.remember_me_check_box)
        loginButton = findViewById(R.id.login_button)
    }
}
