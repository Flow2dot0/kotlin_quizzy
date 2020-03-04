package com.example.quizzy.services

import android.util.Log
import com.example.quizzy.models.MyScore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.time.format.DateTimeFormatter

class FirestoreService {

    val TAG = "Cloud Service"

    // Access a Cloud Firestore instance from your Activity
    val db = FirebaseFirestore.getInstance()

    fun createMyScore(myScore : MyScore) {

        // serialize data for db
        val score = hashMapOf(
            "date" to FieldValue.serverTimestamp(),
            "winrate" to myScore.winrate,
            "correct" to myScore.correct,
            "1" to myScore.first,
            "2" to myScore.second,
            "3" to myScore.third,
            "4" to myScore.fourth,
            "5" to myScore.fifth
        )

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

}