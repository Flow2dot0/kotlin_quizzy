package com.example.quizzy.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.R
import com.example.quizzy.models.MyManager
import kotlinx.android.synthetic.main.activity_results.*


class ResultsActivity : AppCompatActivity() {

    companion object {
        val TAG = "ResultsActivity"
    }

    private val manager = MyManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        manager.retrieveDataFromNavigate(intent, TAG)
        val score = manager.currentScore

        resultsLevelStatus.text = when(score.level){
            0 -> "Newbie"
            1 -> "Between"
            2 -> "GOD"
            3 -> "TITAN"
            else -> "ERROR"
        }
        val status = "${score.correct} / 5"
        correctAnswersStatus.text = status

        val pourcentage = "${score.winrate} %"
        resultsPourcentage.text = pourcentage
        Log.i(TAG, "SCORE FINAL STATE : $score")
        manager.registerScoreToFirestore()
        manager.redirectAfterXSecondsToHome(this)
    }



}
