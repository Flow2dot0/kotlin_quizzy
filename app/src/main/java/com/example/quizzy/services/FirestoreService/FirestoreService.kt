package com.example.quizzy.services.FirestoreService

import android.util.Log
import com.example.quizzy.interfaces.MyCallback
import com.example.quizzy.interfaces.MyCallbackScore
import com.example.quizzy.models.MyQuestion
import com.example.quizzy.models.MyScore
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService : FirestoreServiceInterface {

	override val TAG = "Cloud Service"

	// Access a Cloud Firestore instance from your Activity
	override val db = FirebaseFirestore.getInstance()


	override fun createMyScore(score: HashMap<String, Any?>) {
		// Add a new document with a generated ID
		db.collection("scores")
			.add(score)
			.addOnSuccessListener { documentReference ->
				Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
			}
			.addOnFailureListener { e ->
				Log.w(TAG, "Error adding document", e)
			}
	}


	override fun getAllQuestions(myCallback: MyCallback): MutableList<MyQuestion> {
		var data = mutableListOf<MyQuestion>()
		db.collection("questions")
			.get()
			.addOnSuccessListener { result ->
				data = result.toObjects(MyQuestion::class.java)
				data.sortBy { res -> res.level }
				Log.d(TAG, "=> ${result.toObjects(MyQuestion::class.java)}")
				myCallback.onCallback(data)
			}
			.addOnFailureListener { exception ->
				Log.w(TAG, "Error getting all questions.", exception)
			}

		return data
	}

	// done
	override fun getQuestionsListBasedOnLevel(
		myCallback: MyCallback,
		indexSelected: Int
	): MutableList<MyQuestion> {
		var data = mutableListOf<MyQuestion>()
		db.collection("questions")
			.whereEqualTo("level", indexSelected)
			.get()
			.addOnSuccessListener { result ->
				data = result.toObjects(MyQuestion::class.java)
				Log.d(TAG, "=> ${result.toObjects(MyQuestion::class.java)}")
				myCallback.onCallback(data)
			}
			.addOnFailureListener { exception ->
				Log.w(TAG, "Error getting documents.", exception)
			}


		return data
	}

	override fun getAllScores(myCallback: MyCallbackScore){
		db.collection("scores")
			.get()
			.addOnSuccessListener { result ->
				val data = result.toObjects(MyScore::class.java)
				data.sortBy { res -> res.level }
				Log.d(TAG, "=> ${result.toObjects(MyScore::class.java)}")
				myCallback.onScoreReady(data)
			}
			.addOnFailureListener { exception ->
				Log.w(TAG, "Error getting all questions.", exception)
			}

	}


}