package com.example.quizzy.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzy.QuestionsAdapter
import com.example.quizzy.R
import com.example.quizzy.models.MyManager
import com.example.quizzy.models.MyQuestion
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity(), View.OnClickListener {

	companion object {
		val TAG = "QuestionsActivity"
	}

	private val manager = MyManager()

	private lateinit var adapter: QuestionsAdapter
	private lateinit var allQuestions: List<MyQuestion>


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_questions)

		manager.retrieveDataFromNavigate(intent, TAG)

		Log.i("toto", "allQuestions: ${manager.allQuestions}")

		adapter = QuestionsAdapter(allQuestions, this)

		questions_recycler_view.layoutManager = LinearLayoutManager(this)
		questions_recycler_view.adapter = adapter

	}

	override fun onClick(v: View?) {
		if (v?.tag != null) {
			val index = v.tag as Int
			val question = allQuestions[index]
			Toast.makeText(this, "id: ${question.id}", Toast.LENGTH_SHORT).show()
		}
	}

}
