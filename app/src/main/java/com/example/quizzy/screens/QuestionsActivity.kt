package com.example.quizzy.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzy.QuestionsAdapter
import com.example.quizzy.R
import com.example.quizzy.models.MyQuestion
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

	val questions = Array(100) { i ->
		MyQuestion(
			i,
			"0",
			"path",
			"title $i",
			"answer",
			"choice 1",
			"choice 2",
			"choice3",
			"choice 4",
			"choice5"
		)
	}

	private lateinit var adapter: QuestionsAdapter


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_questions)

		adapter = QuestionsAdapter(questions, this)

		questions_recycler_view.layoutManager = LinearLayoutManager(this)
		questions_recycler_view.adapter = adapter

	}

	override fun onClick(v: View?) {
		if (v?.tag != null) {
			val index = v.tag as Int
			val question = questions[index]
			Toast.makeText(this, "id: ${question.id}", Toast.LENGTH_SHORT).show()
		}
	}

}
