package com.phantomlabs.firequiz.Activity.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.phantomlabs.firequiz.Activity.adapters.OptionAdapter
import com.phantomlabs.firequiz.Activity.models.Questions
import com.phantomlabs.firequiz.Activity.models.Quiz
import com.phantomlabs.firequiz.R

class QuestionActivity : AppCompatActivity() {
    lateinit var optionRecyclerView: RecyclerView
    lateinit var descriptiontxt: TextView
    var quizzes : MutableList<Quiz> ?= null
    var question : MutableMap<String,Questions> ?= null
    lateinit var btnPrev : Button
    lateinit var btnNext : Button
    lateinit var btnSubmit : Button
    var index = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        optionRecyclerView = findViewById(R.id.optionList)
        descriptiontxt = findViewById(R.id.description)
        btnPrev = findViewById(R.id.prv_btn)
        btnNext = findViewById(R.id.nxt_btn)
        btnSubmit = findViewById(R.id.submit_btn)
        setUpFirestore()
    }

    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("quizzes").whereEqualTo("title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        Log.d("DATA",it.toObjects(Quiz::class.java).toString())
                        quizzes = it.toObjects(Quiz::class.java)
                        question = quizzes!![0].question
                        bindViews()
                    }
                }
        }

    }

    private fun bindViews() {
        btnPrev.visibility = View.GONE
        btnNext.visibility = View.GONE
        btnSubmit.visibility = View.GONE

        if(index == 1) { //first quiz item
            btnNext.visibility = View.VISIBLE
        } else if(index == question!!.size) { // last quiz item
            btnPrev.visibility = View.VISIBLE
            btnSubmit.visibility = View.VISIBLE
        } else { // middle quiz item
            btnPrev.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }

        val questions = question!!["question$index"]
        questions?.let {
            descriptiontxt.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            optionRecyclerView.layoutManager = LinearLayoutManager(this)
            optionRecyclerView.adapter = optionAdapter
            optionRecyclerView.setHasFixedSize(true)
        }
    }
}