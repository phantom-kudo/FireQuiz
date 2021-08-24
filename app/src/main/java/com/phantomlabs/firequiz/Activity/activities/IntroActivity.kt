package com.phantomlabs.firequiz.Activity.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.phantomlabs.firequiz.R
import java.lang.Exception

class IntroActivity : AppCompatActivity() {
    lateinit var getstartedbtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        getstartedbtn = findViewById(R.id.btn_get_started)

        if(auth.currentUser != null) {
            redirect("MAIN")
        }
        getstartedbtn.setOnClickListener{
            redirect("LOGIN")
        }
    }

    private fun redirect(name:String) {
        val intent = when(name) {
            "LOGIN" -> Intent(this, LoginActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("No path exists!!!")
        }
        startActivity(intent)
        finish()
    }

}