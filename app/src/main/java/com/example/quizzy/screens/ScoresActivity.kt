package com.example.quizzy.screens

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzy.R
import com.example.quizzy.ScoresAdapter
import com.example.quizzy.models.MyManager
import com.example.quizzy.models.MyScore
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity : AppCompatActivity() {

	companion object {
		val TAG = "ScoresActivity"
	}

	private val manager = MyManager()

	private lateinit var adapter: ScoresAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_scores)

		manager.retrieveDataFromNavigate(intent, TAG)

		Log.i("ScoresActivity", "ScoresActivity: ${manager.allScores}")

		adapter = ScoresAdapter(manager.allScores)

		scores_recycler_view.layoutManager = LinearLayoutManager(this)
		scores_recycler_view.adapter = adapter

	}


}
