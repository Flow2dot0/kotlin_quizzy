package com.example.quizzy.models

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import com.example.quizzy.HomeActivity
import com.example.quizzy.interfaces.MyCallback
import com.example.quizzy.interfaces.MyCallbackScore
import com.example.quizzy.screens.*
import com.example.quizzy.services.FirestoreService.FirestoreService
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

class MyManager {


	val TAG = "MANAGER"

	val firestore = FirestoreService()

	var indexQuestion: Int = 0
	lateinit var allQuestions: ArrayList<MyQuestion>
	lateinit var allScores: ArrayList<MyScore>
	lateinit var score: ArrayList<MyScore>
	lateinit var question: ArrayList<MyQuestion>
	lateinit var questionsData: List<MyQuestion>
	lateinit var currentListQuestions: ArrayList<MyQuestion>
	lateinit var currentScore: MyScore
	lateinit var pathImageToFullScreen: String
	var statusQuestionNumberVisibility: Boolean = true


	fun serializeScore(myScore: MyScore): HashMap<String, Any?> {
		// serialize data for db
		return hashMapOf(
			"date" to FieldValue.serverTimestamp(),
			"winrate" to myScore.winrate,
			"correct" to myScore.correct,
			"level" to myScore.level,
			"1" to myScore.first,
			"2" to myScore.second,
			"3" to myScore.third,
			"4" to myScore.fourth,
			"5" to myScore.fifth
		)
	}

	fun deserializeScore(map: HashMap<String, Any?>): MyScore {
		return MyScore(
			map["date"] as Timestamp?,
			map["first"] as Boolean?,
			map["second"] as Boolean?,
			map["third"] as Boolean?,
			map["fourth"] as Boolean?,
			map["fifth"] as Boolean?,
			map["correct"] as Int?,
			map["winrate"] as Int?,
			map["level"] as Int?
		)
	}

	fun deserializedQuestion(map: HashMap<String, Any?>): MyQuestion {
		return MyQuestion(
			map["id"] as Int,
			map["level"] as Int,
			map["path"] as String,
			map["title"] as String,
			map["answer"] as String,
			map["A"] as String,
			map["B"] as String,
			map["C"] as String,
			map["D"] as String,
			map["pathMode"] as String
		)
	}

	fun registerScoreToFirestore() {
		val map = serializeScore(currentScore)
		firestore.createMyScore(map)
	}

	fun navigateToCredits() {}

	fun navigateToAllQuestions() {}

	fun navigateToUniqueQuestion() {}

	fun redirectAfterXSecondsToHome(context: Context, milliseconds: Long = 2000) {
		val r = Runnable {
			this.navigateToWithData(HomeActivity.TAG, context)
		}
		val h = Handler()

		h.postDelayed(r, milliseconds)
	}

	fun getListOfQuestionFromDB(indexSelected: Int, context: Context) {

		firestore.getQuestionsListBasedOnLevel(object : MyCallback {
			override fun onCallback(value: List<MyQuestion>) {
				questionsData = value
				Log.i(TAG, "I GET MY DATA FROM DB : $questionsData")
				// randomise the current list
				currentListQuestions = questionsData.shuffled() as ArrayList<MyQuestion>
				// navigate to Game Activity
				navigateToWithData(GameActivity.TAG, context)
			}
		}, indexSelected)
	}


	fun retrieveDataFromNavigate(intent: Intent, string: String) {
		when (string) {
			GameActivity.TAG -> {
				currentListQuestions = intent.getParcelableArrayListExtra<MyQuestion>("myQuestions")
				currentScore = intent.getParcelableExtra<MyScore>("myScore")
				indexQuestion = intent.getIntExtra("indexQuestion", 10)
				statusQuestionNumberVisibility =
					intent.getBooleanExtra("statusQuestionNumberVisibility", true)

				Log.i(TAG, "MY QUESTIONS ARE : $currentListQuestions")
				Log.i(TAG, "MY CURRENT SCORE IS :  $currentScore")
				Log.i(TAG, "THE INDEX IS :  $indexQuestion")
			}
			QuestionsActivity.TAG -> {
				allQuestions =
					intent.getParcelableArrayListExtra<MyQuestion>("allQuestions") as ArrayList
				Log.i(TAG, "allQuestions ARE : $allQuestions")
			}
			ScoresActivity.TAG -> {
				allScores =
					intent.getParcelableArrayListExtra<MyScore>("allScores") as ArrayList
				Log.i(TAG, "allScores ARE : $allScores")
			}
			ResultsActivity.TAG -> {
				currentScore = intent.getParcelableExtra<MyScore>("myScore")
				Log.i(TAG, "MY CURRENT SCORE IS :  $currentScore")
			}
			FullScreenImageActivity.TAG -> {
				pathImageToFullScreen = intent.getStringExtra("path")
				Log.i(TAG, "MY PATH IMAGE :  $pathImageToFullScreen")
			}
		}

	}

	fun navigateToWithData(
		activity: String,
		context: Context
	) {
		when (activity) {
			GameActivity.TAG -> {
				val scoreToSend = MyScore(level = indexQuestion)

				Log.i("scoreToSend", scoreToSend.toString())
				val intent = Intent(context, GameActivity::class.java)
				intent.putExtra("statusQuestionNumberVisibility", true)
				intent.putParcelableArrayListExtra("myQuestions", currentListQuestions)
				intent.putExtra("myScore", scoreToSend)
				intent.putExtra("indexQuestion", indexQuestion)
				context.startActivity(intent)
			}
			QuestionsActivity.TAG -> {
				val intent = Intent(context, QuestionsActivity::class.java)
				intent.putParcelableArrayListExtra("allQuestions", allQuestions)
				context.startActivity(intent)
			}
			ScoresActivity.TAG -> {
				val intent = Intent(context, ScoresActivity::class.java)
				intent.putParcelableArrayListExtra("allScores", allScores)
				context.startActivity(intent)
			}
			ResultsActivity.TAG -> {
				val intent = Intent(context, ResultsActivity::class.java)
				currentScore.winrate = currentScore.correct?.times(20)
				intent.putExtra("myScore", currentScore)
				context.startActivity(intent)
			}
			HomeActivity.TAG -> {
				val intent = Intent(context, HomeActivity::class.java)
				context.startActivity(intent)
			}
			"${GameActivity.TAG}2" -> {
				val intent = Intent(context, GameActivity::class.java)
				intent.putExtra("statusQuestionNumberVisibility", false)
				intent.putParcelableArrayListExtra("myQuestions", question)
				intent.putExtra("myScore", MyScore())
				intent.putExtra("indexQuestion", 0)
				context.startActivity(intent)
			}
			FullScreenImageActivity.TAG -> {
				val intent = Intent(context, FullScreenImageActivity::class.java)
				intent.putExtra("path", pathImageToFullScreen)
				context.startActivity(intent)

			}

		}
	}

	fun getAllQuestionsFromDB(context: Context) {
		firestore.getAllQuestions(object : MyCallback {
			override fun onCallback(value: List<MyQuestion>) {
				allQuestions = value as ArrayList<MyQuestion>
				Log.i(TAG, "I GET MY allQuestions FROM DB : $allQuestions")
				navigateToWithData(QuestionsActivity.TAG, context)
			}
		})
	}

	fun getAllScoresFromDB(context: Context) {
		firestore.getAllScores(object : MyCallbackScore {
			override fun onScoreReady(value: List<MyScore>) {
				allScores = value as ArrayList<MyScore>
				Log.i(TAG, "I GET MY allScores FROM DB : $allScores")
				navigateToWithData(ScoresActivity.TAG, context)
			}

		})
	}


}