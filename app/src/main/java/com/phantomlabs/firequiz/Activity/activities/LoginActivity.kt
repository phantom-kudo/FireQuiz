package com.phantomlabs.firequiz.Activity.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.phantomlabs.firequiz.R

class LoginActivity : AppCompatActivity() {
    lateinit var firebaseAuth : FirebaseAuth
    lateinit var signinbtn : Button
    lateinit var signinemail : EditText
    lateinit var signinpassword : EditText
    lateinit var signupintent : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        signinbtn = findViewById(R.id.signin_btn)
        signinemail = findViewById(R.id.et_signin_email)
        signinpassword = findViewById(R.id.et_signin_password)
        signupintent = findViewById(R.id.dont_have_acc)

        signupintent.setOnClickListener {
            val intent2signup = Intent(this, SignupActivity::class.java)
            startActivity(intent2signup)
            finish()
        }

        signinbtn.setOnClickListener{
            login()
        }

    }

    private fun login() {
        val email : String = signinemail.text.toString()
        val password : String = signinpassword.text.toString()

        if(email.isBlank() || password.isBlank()) {
            Toast.makeText(this,"Enter the details properly", Toast.LENGTH_SHORT).show()
            return
        }


        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if(it.isSuccessful) {
                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,"Error!!", Toast.LENGTH_SHORT).show()
                }
            }


    }
}