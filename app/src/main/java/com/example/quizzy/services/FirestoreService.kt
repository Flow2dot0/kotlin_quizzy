package com.example.quizzy.services

import android.util.Log
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

    fun getQuestionsListBasedOnLevel(indexSelectRadio : Int) : List<HashMap<String, Any?>> {
        val levelStatus = when(indexSelectRadio){
            0 -> "newbie"
            1 -> "between"
            else -> "god"
        }
        val l = mutableListOf<HashMap<String, Any?>>()

        db.collection("questions")
            .whereEqualTo("level", levelStatus)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    // TODO : get the data as hashmap and add to list, final returning the list

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return l

    }



}