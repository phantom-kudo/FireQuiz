package com.phantomlabs.firequiz.Activity.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.phantomlabs.firequiz.Activity.adapters.QuizAdapter
import com.phantomlabs.firequiz.Activity.models.Quiz
import com.phantomlabs.firequiz.R
import java.sql.Date

class MainActivity : AppCompatActivity() {
    lateinit var firestore : FirebaseFirestore
    lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    lateinit var appbar : MaterialToolbar
    lateinit var drawerLayout : DrawerLayout
    lateinit var adapter : QuizAdapter
    lateinit var btnDatePicker : FloatingActionButton
    private var quizlist = mutableListOf<Quiz>()
    lateinit var quizRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appbar = findViewById(R.id.topAppBar)
        drawerLayout = findViewById(R.id.main_drawer_layout)
        quizRecyclerView = findViewById(R.id.mainRecyclerView)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        setUpViews()
    }


    private fun setUpViews() {
        setUpFirestore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER",datePicker.headerText)
                val dateFormatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    SimpleDateFormat("dd-mm-yyyy")
                } else {
                    TODO("VERSION.SDK_INT < N")
                }
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener{
                Log.d("DATEPICKER",datePicker.headerText)

            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER","DatePicker was Cancelled!!")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")

        collectionReference.addSnapshotListener { value, error ->
            if(value == null || error != null) {
                Toast.makeText(this,"Error fetching Data!!", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            quizlist.clear()
            quizlist.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this,quizlist)
        quizRecyclerView.layoutManager = GridLayoutManager(this,2)
        quizRecyclerView.adapter = adapter
    }

    private fun setUpDrawerLayout() {
        setSupportActionBar(appbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name)
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}