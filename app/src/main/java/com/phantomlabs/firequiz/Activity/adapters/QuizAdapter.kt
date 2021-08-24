package com.phantomlabs.firequiz.Activity.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.phantomlabs.firequiz.Activity.activities.QuestionActivity
import com.phantomlabs.firequiz.Activity.models.Quiz
import com.phantomlabs.firequiz.Activity.utils.ColorPicker
import com.phantomlabs.firequiz.Activity.utils.IconPicker
import com.phantomlabs.firequiz.R

class QuizAdapter(val context: Context,val quizzes: List<Quiz>)
    : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false)
        return QuizViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.textViewTitle.text = quizzes[position].title
        holder.cardcontainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()))
       // holder.iconview.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener{
            Toast.makeText(context,quizzes[position].title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context, QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle : TextView = itemView.findViewById(R.id.quiz_title)
        var iconview : ImageView = itemView.findViewById(R.id.quiz_icon)
        var cardcontainer : CardView = itemView.findViewById(R.id.item_cardcontainer)
    }

}