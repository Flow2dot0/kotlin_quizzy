package com.example.quizzy.services

import android.util.Log
import com.example.quizzy.interfaces.MyCallback
import com.example.quizzy.models.MyQuestion
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService {

    val TAG = "Cloud Service"

    // Access a Cloud Firestore instance from your Activity
    val db = FirebaseFirestore.getInstance()


    fun createMyScore(score : HashMap<String, Any?>) {
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


    // done
    fun getQuestionsListBasedOnLevel(myCallback: MyCallback, indexSelected: Int): MutableList<MyQuestion> {
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






}