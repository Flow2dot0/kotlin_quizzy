package com.example.quizzy.models

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.quizzy.interfaces.MyCallback
import com.example.quizzy.screens.GameActivity
import com.example.quizzy.screens.QuestionsActivity
import com.example.quizzy.services.FirestoreService.FirestoreService
import com.google.firebase.firestore.FieldValue

class MyManager {

	val TAG = "MANAGER"

	private val firestore =
		FirestoreService()

	var indexQuestion: Int = 0
	lateinit var questionsData: List<MyQuestion>

	lateinit var allQuestions: MutableList<MyQuestion>
	lateinit var currentListQuestions: ArrayList<MyQuestion>
	lateinit var currentScore: MyScore


	fun serializeScore(myScore: MyScore): HashMap<String, Any?> {
		// serialize data for db
		return hashMapOf(
			"date" to FieldValue.serverTimestamp(),
			"winrate" to myScore.winrate,
			"correct" to myScore.correct,
			"1" to myScore.first,
			"2" to myScore.second,
			"3" to myScore.third,
			"4" to myScore.fourth,
			"5" to myScore.fifth
		)
	}

	fun deserializeScore(map: HashMap<String, Any?>): MyScore {
		return MyScore(
			map["date"] as String?,
			map["first"] as Boolean?,
			map["second"] as Boolean?,
			map["third"] as Boolean?,
			map["fourth"] as Boolean?,
			map["fifth"] as Boolean?,
			map["correct"] as Int?,
			map["winrate"] as Int?
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
			map["D"] as String
		)
	}

	fun incrementQuestionStatus() {}

	fun changeUiStatusForQuickResult() {}

	fun navigateToCredits() {}

	fun navigateToGame() {}

	fun navigateToResults() {}

	fun navigateToAllQuestions() {}

	fun navigateToHome() {}

	fun navigateToUniqueQuestion() {}

	// BONUS
	fun getListOfQuestionFromAPI() {}

	fun getListOfQuestionFromDB(indexSelected: Int, context: Context) {

		firestore.getQuestionsListBasedOnLevel(object : MyCallback {
			override fun onCallback(value: List<MyQuestion>) {
				questionsData = value
				Log.i(TAG, "I GET MY currentListQuestions FROM DB : $questionsData")
				// randomise the current list
				currentListQuestions = questionsData.shuffled() as ArrayList<MyQuestion>
				// navigate to Game Activity
				navigateToWithData(GameActivity.TAG, context, currentListQuestions)
			}
		}, indexSelected)
	}

	fun getAllQuestionsFromDB(context: Context) {
		firestore.getAllQuestions(object : MyCallback {
			override fun onCallback(value: List<MyQuestion>) {
				allQuestions = value as ArrayList<MyQuestion>
				Log.i(TAG, "I GET MY allQuestions FROM DB : $allQuestions")
				navigateToWithData(QuestionsActivity.TAG, context,allQuestions as ArrayList<MyQuestion>)
			}
		})
	}

	fun navigateToWithData(
		activity: String,
		context: Context,
		questions: ArrayList<MyQuestion>?,
		myScore: MyScore? = MyScore()
	) {

		when (activity) {
			GameActivity.TAG -> {
				val intent = Intent(context, GameActivity::class.java)
				intent.putParcelableArrayListExtra("myQuestions", questions)
				intent.putExtra("myScore", myScore)
				intent.putExtra("indexQuestion", indexQuestion)
				context.startActivity(intent)
			}
			QuestionsActivity.TAG -> {
				val intent = Intent(context, QuestionsActivity::class.java)
				intent.putParcelableArrayListExtra("allQuestions", questions)
				context.startActivity(intent)
			}
		}
	}

	fun retrieveDataFromNavigate(intent: Intent, string: String) {
		when (string) {
			GameActivity.TAG -> {
				currentListQuestions = intent.getParcelableArrayListExtra<MyQuestion>("myQuestions")
				currentScore = intent.getParcelableExtra<MyScore>("myScore")
				indexQuestion = intent.getIntExtra("indexQuestion", 10)

				Log.i(TAG, "MY QUESTIONS ARE : $currentListQuestions")
				Log.i(TAG, "MY CURRENT SCORE IS :  $currentScore")
				Log.i(TAG, "THE INDEX IS :  $indexQuestion")
			}
			QuestionsActivity.TAG -> {
				allQuestions = intent.getParcelableArrayListExtra<MyQuestion>("allQuestions") as ArrayList
				Log.i(TAG, "allQuestions ARE : $allQuestions")
			}
		}

	}

	fun randomQuestions() {}
}