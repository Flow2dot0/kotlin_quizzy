package com.example.quizzy.services

import android.util.Log
import com.example.quizzy.models.MyQuestion
import com.example.quizzy.models.MyScore
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.time.format.DateTimeFormatter

class FirestoreService {

    val TAG = "Cloud Service"

    // Access a Cloud Firestore instance from your Activity
    val db = FirebaseFirestore.getInstance()

    fun createMyScore(myScore : MyScore) {


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

    fun getQuestionsListBasedOnLevel(indexSelectAlertdialog : Int) : List<MyQuestion> {
        val levelStatus = when(indexSelectAlertdialog){
            0 -> "newbie"
            1 -> "between"
            else -> "god"
        }
        var l = mutableListOf<MyQuestion>()

        db.collection("questions")
            .whereEqualTo("level", levelStatus)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return l

    }

    fun serialize(myScore : MyScore) {
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

    }

    fun deserialize() {

    }

}