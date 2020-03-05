package com.example.quizzy.services.FirestoreService

import com.example.quizzy.interfaces.MyCallback
import com.example.quizzy.models.MyQuestion
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreServiceStub {

	val TAG = "FirestoreServiceStub"

	// Access a Cloud Firestore instance from your Activity
	val db = FirebaseFirestore.getInstance()


	fun createMyScore(score: HashMap<String, Any?>) {
// ToDO
	}


	fun getAllQuestions(myCallback: MyCallback): MutableList<MyQuestion> {

		return MutableList(100) { i ->
			MyQuestion(
				i,
				0,
				"path",
				"getAllQuestions $i",
				"answer",
				"A",
				"B",
				"C",
				"D"
			)
		}
	}


	fun getQuestionsListBasedOnLevel(
		myCallback: MyCallback,
		indexSelected: Int
	): MutableList<MyQuestion> {
		return MutableList(5) { i ->
			MyQuestion(
				i,
				0,
				"path",
				"QuestionsListBasedOnLevel $i",
				"answer",
				"A",
				"B",
				"C",
				"D"
			)
		}
	}


}