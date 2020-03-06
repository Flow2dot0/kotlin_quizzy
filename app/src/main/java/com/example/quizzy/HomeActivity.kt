package com.example.quizzy

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.models.MyManager
import com.example.quizzy.screens.CreditsActivity
import com.example.quizzy.screens.ScoresActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

	companion object {
		val TAG = "HomeActivity"
	}

	private val manager = MyManager()

	private val levels =
		arrayOf(
			Html.fromHtml("<font color='#ffffff'>Newbie</font>"),
			Html.fromHtml("<font color='#ffffff'>Between</font>"),
			Html.fromHtml("<font color='#ffffff'>GOD</font>")
		)

	var selectedLevelIndex = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		playButton.setOnClickListener {
			showAlertDialog()
		}

		questionsButton.setOnClickListener {
			loadData("getAllQuestionsFromDB")
		}

		scoresButton.setOnClickListener {
			loadData("getAllScoreFromDB")

		}

		aboutButton.setOnClickListener {
			val intent = Intent(this, CreditsActivity::class.java)
			startActivity(intent)
		}
	}

	// Alert with selected level options, bonus : funny toasts
	private fun showAlertDialog() {
		val alertDialog =
			AlertDialog.Builder(this@HomeActivity, R.style.AlertDialogStyle)
		alertDialog.setTitle("How strong are you toto?")
		alertDialog.setSingleChoiceItems(
			levels,
			selectedLevelIndex
		) { dialog, which ->
			when (which) {
				0 -> {
					selectedLevelIndex = which
					Log.i(TAG, "Current level selected : $selectedLevelIndex")
					Toast.makeText(
						this@HomeActivity,
						"No worries, it will be fine...",
						Toast.LENGTH_SHORT
					).show()
				}

				1 -> {
					selectedLevelIndex = which
					Log.i(TAG, "Current level selected : $selectedLevelIndex")

					Toast.makeText(
						this@HomeActivity,
						"Let's upgrade a bit the difficulty",
						Toast.LENGTH_SHORT
					).show()
				}
				2 -> {
					selectedLevelIndex = which
					Log.i(TAG, "Current level selected : $selectedLevelIndex")

					Toast.makeText(
						this@HomeActivity,
						"Are you really one ?",
						Toast.LENGTH_SHORT
					).show()

				}
			}
		}
		val alert = alertDialog.create()
		alert.setCanceledOnTouchOutside(true)
		alert.setOnCancelListener {
			loadData("getListOfQuestionFromDB")
		}
		alert.show()
	}

	private fun loadData(params: String) {

		when (params) {
			"getListOfQuestionFromDB" -> {
				manager.getListOfQuestionFromDB(selectedLevelIndex, this)
			}
			"getAllQuestionsFromDB" -> {
				manager.getAllQuestionsFromDB(this)
			}	"getAllScoreFromDB" -> {
				manager.getAllScoresFromDB(this)
			}
		}

	}


}
