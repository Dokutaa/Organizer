package com.example.organizer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.organizer.R
import com.example.organizer.loadImage
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var loginScreenImageView: ImageView
    private lateinit var loginTextView: EditText
    private lateinit var passwordTextView: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        val image = "https://i.imgur.com/DvpvklR.png"
        val login = "Dokutaa"
        val password = "231daehraw"

        loginScreenImageView = findViewById(R.id.login_screen_image_view)
        loginTextView = findViewById(R.id.login_edit_text)
        passwordTextView = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)

        loadImage(image, loginScreenImageView)

        loginButton.onClick {

            var enteredLogin = loginTextView.text.toString()
            var enteredPassword = passwordTextView.text.toString()

            if (enteredLogin == login && enteredPassword == password) {
                startActivity<MainActivity>()
            } else {
                alert("Wrong login or password").show()
            }

        }



    }
}
