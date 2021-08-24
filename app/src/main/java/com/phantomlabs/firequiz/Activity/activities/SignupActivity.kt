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

class SignupActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var signupbtn : Button
    lateinit var signupemail : EditText
    lateinit var signuppassword : EditText
    lateinit var signupconpassword : EditText
    lateinit var signinintent : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firebaseAuth = FirebaseAuth.getInstance()
        signupbtn = findViewById(R.id.signup_btn)
        signupemail = findViewById(R.id.et_signup_email)
        signuppassword = findViewById(R.id.et_signup_password)
        signupconpassword = findViewById(R.id.et_signup_conpassword)
        signinintent = findViewById(R.id.alr_have_acc)

        signinintent.setOnClickListener {
            val intent2signup = Intent(this, LoginActivity::class.java)
            startActivity(intent2signup)
            finish()
        }

        signupbtn.setOnClickListener{
            signUpUser()
        }

    }

    private fun signUpUser() {
        val email : String = signupemail.text.toString()
        val password : String = signuppassword.text.toString()
        val conpassword : String = signupconpassword.text.toString()

        if(email.isBlank() || password.isBlank() || conpassword.isBlank()) {
            Toast.makeText(this,"Enter the details properly",Toast.LENGTH_SHORT).show()
            return
        }
        if(password != conpassword) {
            Toast.makeText(this,"Password doesn't match!!",Toast.LENGTH_SHORT).show()
            return
        }

        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if(it.isSuccessful) {
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,"Error!!",Toast.LENGTH_SHORT).show()
                }
            }


    }


}