package com.example.quizzy.services.FirestoreService

import com.example.quizzy.interfaces.MyCallback
import com.example.quizzy.models.MyQuestion
import com.google.firebase.firestore.FirebaseFirestore

interface FirestoreServiceInterface {

	val TAG: String

	// Access a Cloud Firestore instance from your Activity
	val db: FirebaseFirestore
	fun createMyScore(score: HashMap<String, Any?>)


	fun getAllQuestions(myCallback: MyCallback): MutableList<MyQuestion>

	fun getQuestionsListBasedOnLevel(
		myCallback: MyCallback,
		indexSelected: Int
	): MutableList<MyQuestion>
}