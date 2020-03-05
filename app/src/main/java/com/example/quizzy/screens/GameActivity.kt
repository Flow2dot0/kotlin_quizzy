package com.example.quizzy.screens

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.quizzy.R
import com.example.quizzy.models.MyManager
import com.example.quizzy.models.MyQuestion
import com.example.quizzy.models.MyScore
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

	companion object {
		val TAG = "Game Activity"
	}

	private val RECOVERY_DIALOG_REQUEST = 1

	private val manager = MyManager()
	lateinit var currentQuestion: MyQuestion
	lateinit var choices: MutableList<String>
	lateinit var path: String
	lateinit var correct: String
	lateinit var score: MyScore
	var userAnswer by Delegates.notNull<Boolean>()
	lateinit var youTubePlayerFragment: YouTubePlayerSupportFragment


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_game)
		manager.retrieveDataFromNavigate(intent, TAG)

		statusQuestionNumber.visibility =
			if (manager.statusQuestionNumberVisibility) View.VISIBLE else View.INVISIBLE


		youTubePlayerFragment =
			(supportFragmentManager.findFragmentById(R.id.third_party_player_view) as YouTubePlayerSupportFragment?)!!
//		youTubePlayerFragment.initialize("AIzaSyB5hIzmoI7JANpXYWQLm4liboActq_VXUQ", this)
		updateUI()
		score.correct = 0
		score.level = currentQuestion.level
		handleUserSelection()
        levelStatus.text = when(currentQuestion.level){
            0 -> "Newbie"
            1 -> "Between"
            2 -> "GOD"
            3 -> "TITAN"
            else -> "ERROR"
        }
	}

	// On success
	override fun onInitializationSuccess(
		provider: YouTubePlayer.Provider,
		youTubePlayer: YouTubePlayer,
		wasRestored: Boolean
	) {
		if (!wasRestored) {
			youTubePlayer.cueVideo(path)
		}
	}

	// On error
	override fun onInitializationFailure(
		provider: YouTubePlayer.Provider,
		youTubeInitializationResult: YouTubeInitializationResult
	) {
		if (youTubeInitializationResult.isUserRecoverableError) {
			youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
		} else {
			val errorMessage = String.format(
				"There was an error initializing the YouTubePlayer (%1\$s)",
				youTubeInitializationResult.toString()
			)
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
		}
	}

	fun randomiseChoices() {
		choices = mutableListOf<String>(
			currentQuestion.A!!,
			currentQuestion.B!!, currentQuestion.C!!, currentQuestion.D!!
		)
		choices.shuffle()
	}

	fun handleChoiceSelection() {
		// Get radio group selected item using on checked change listener
		radioGroup.setOnCheckedChangeListener { group, checkedId ->
			val radio: RadioButton = findViewById(checkedId)
			Toast.makeText(this, "Selected : ${radio.text}", Toast.LENGTH_SHORT).show()
		}
	}

	fun handleUserSelection() {
		nextButton.setOnClickListener {
			var id: Int = radioGroup.checkedRadioButtonId
			if (id != -1) {
				val radio: RadioButton = findViewById(id)
				if (radio.text == correct) {
					// TODO : change background color to GREEN
					userAnswer = true
					score.correct = score.correct?.plus(1)
					val toast = Toast.makeText(
						this, "Right :" +
								" ${radio.text}",
						Toast.LENGTH_SHORT
					)
					toast.setGravity(Gravity.TOP or Gravity.RIGHT, 20, 20)
					toast.show()

				} else {
					// TODO : change background color to RED
					userAnswer = false
					val toast = Toast.makeText(
						this, "False :" +
								" ${correct}",
						Toast.LENGTH_SHORT
					)
					toast.setGravity(Gravity.TOP or Gravity.RIGHT, 20, 20)
					toast.show()
				}

			if (manager.question.size == 1) {
					manager.navigateToWithData(
						QuestionsActivity.TAG,
						this
					)
				}

				runBlocking {
					delay(500)
					nextButton.text = "NEXT"
				}

				nextButton.setOnClickListener {
					// TODO : add question index number answer TRUE|FALSE on score

					when (manager.indexQuestion) {
						0 -> score.first = userAnswer
						1 -> score.second = userAnswer
						2 -> score.third = userAnswer
						3 -> score.fourth = userAnswer
						4 -> score.fifth = userAnswer
					}

					if (manager.indexQuestion < 4 && manager.indexQuestion >= 0) {
						// TODO : increment question
						// TODO : update UI
						manager.indexQuestion++
						updateUI()
						Log.i(TAG, "MY SCORE IS NOW : ${manager.currentScore}")
						handleUserSelection()
					} else {
						// TODO : at end of list question navigate to Results Activity
						manager.navigateToWithData(ResultsActivity.TAG, this)
						// TODO : then after 3 seconds navigate to HomeActivity
					}
				}


			} else {
				// Redirect in case of errors to Home
				Toast.makeText(
					this, "Something wrong :" +
							" nothing selected",
					Toast.LENGTH_LONG
				).show()
				manager.redirectAfterXSecondsToHome(this, 4000)
			}
		}
	}

	fun updateUI() {
		currentQuestion = manager.currentListQuestions[manager.indexQuestion]
		correct = currentQuestion.answer.toString()
		path = currentQuestion.path.toString()
		score = manager.currentScore
		nextButton.text = "OK"
		val qNumber = "${manager.indexQuestion + 1} / 5"
		statusQuestionNumber.text = qNumber
		// init yt player
		youTubePlayerFragment.onDestroy()
		// TODO : display value for each radio button
		questionTitle.setText("${currentQuestion.title}")
		randomiseChoices()
		radioButton.setText(choices[0])
		radioButton2.setText(choices[1])
		radioButton3.setText(choices[2])
		radioButton4.setText(choices[3])

		// TODO : detect path status
		// set visibilities
		when(currentQuestion.pathMode){
			"YT" -> {
				linearLayout.visibility = View.INVISIBLE
				third_party_player_view.isVisible
				youTubePlayerFragment.initialize("AIzaSyB5hIzmoI7JANpXYWQLm4liboActq_VXUQ", this)
			}
			"IMG" -> {
				linearLayout.visibility = View.VISIBLE
				third_party_player_view.isHidden
				val imageView = ImageView(this)
				Glide.with(this).load(currentQuestion.path).into(imageView)
				linearLayout.addView(imageView)
			}
		}

		// path is URI
		// on click display alert dialog with image bigger
		// cancelable true

		// path is youtube ID
	}

}


