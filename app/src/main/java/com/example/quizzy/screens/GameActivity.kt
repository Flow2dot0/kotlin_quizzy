package com.example.quizzy.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.quizzy.R
import com.example.quizzy.models.MyManager
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    val manager = MyManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var isDone = View.INVISIBLE

        card2.visibility = isDone

        // TODO : display value for each radio button

        var playSoundButton = playSoundButton.setOnClickListener {

        }


        // TODO : get value of selected radio if OK pressed

        // TODO : display result and changeuistatus

        // TODO : reformat ui with next question and status index question

        // TODO : incrementing ScoreModel each question

        // TODO : at the last question navigate to ResultsActivity
        // TODO : then after 3 seconds navigate to HomeActivity

    }
}
