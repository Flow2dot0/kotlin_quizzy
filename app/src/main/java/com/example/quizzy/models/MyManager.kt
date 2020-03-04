package com.example.quizzy.models

import com.google.firebase.firestore.FieldValue

class MyManager {

    fun serializeScore(myScore : MyScore): HashMap<String, Any?> {
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

    fun deserializeScore(map : HashMap<String, Any?>) : MyScore {
        return MyScore(map["date"] as String?, map["first"] as Boolean?, map["second"] as Boolean?, map["third"] as Boolean?, map["fourth"] as Boolean?, map["fifth"] as Boolean?, map["correct"] as Int?, map["winrate"] as Int?)
    }

    fun deserializedQuestion(map : HashMap<String, Any?>) : MyQuestion{
        return MyQuestion(map["id"] as Int, map["level"] as String, map["path"] as String, map["title"] as String, map["answer"] as String, map["choice1"] as String, map["choice2"] as String, map["choice3"] as String, map["choice4"] as String, map["choice5"] as String)
    }

}