package com.example.quizzy.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.R
import com.example.quizzy.models.MyManager
import com.example.quizzy.models.MyQuestion
import com.example.quizzy.models.MyScore
import kotlinx.android.synthetic.main.activity_game.*
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class GameActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    private val TAG = "Game Activity"
    private val RECOVERY_DIALOG_REQUEST = 1

    private val manager = MyManager()
    lateinit var currentQuestion : MyQuestion
    lateinit var choices : MutableList<String>
    lateinit var path : String
    lateinit var correct : String

    private var isDone = View.INVISIBLE



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        manager.retrieveDataFromNavigate(intent)
        currentQuestion = manager.currentListQuestions[manager.indexQuestion]
        correct = currentQuestion.answer.toString()
        path = currentQuestion.path.toString()
        // hide the done box result
//        card2.visibility = isDone
        nextButton.setText("OK")

        // init yt player
        val youTubePlayerFragment = supportFragmentManager.findFragmentById(R.id.third_party_player_view) as YouTubePlayerSupportFragment?
        youTubePlayerFragment?.initialize("AIzaSyB5hIzmoI7JANpXYWQLm4liboActq_VXUQ", this)

        // TODO : display value for each radio button
        questionTitle.setText("${currentQuestion.title}")
        randomiseChoices()
        radioButton.setText(choices[0])
        radioButton2.setText(choices[1])
        radioButton3.setText(choices[2])
        radioButton4.setText(choices[3])

        handleChoiceSelection()

        nextButton.setOnClickListener {
            var id: Int = radioGroup.checkedRadioButtonId
            if(id!=-1){
                val radio : RadioButton = findViewById(id)
                if(radio.text == correct){

                    Toast.makeText(this,"Correct :" +
                            " ${radio.text}",
                        Toast.LENGTH_LONG).show()
                }
                runBlocking {
                    delay(2000)
                    nextButton.setText("NEXT")
                }

            }else{
                Toast.makeText(applicationContext,"Something wrong :" +
                        " nothing selected",
                    Toast.LENGTH_LONG).show()
            }
        }


        // TODO : get value of selected radio if OK pressed

        // TODO : display result and changeuistatus

        // TODO : reformat ui with next question and status index question

        // TODO : incrementing ScoreModel each question

        // TODO : at the last question navigate to ResultsActivity
        // TODO : then after 3 seconds navigate to HomeActivity

    }

    // On success
    override fun onInitializationSuccess(provider: YouTubePlayer.Provider,youTubePlayer: YouTubePlayer,wasRestored: Boolean) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(path)
        }
    }

    // On error
    override fun onInitializationFailure(provider: YouTubePlayer.Provider,youTubeInitializationResult: YouTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show()
        } else {
            val errorMessage = String.format(
                "There was an error initializing the YouTubePlayer (%1\$s)",
                youTubeInitializationResult.toString()
            )
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun randomiseChoices(){
        choices = mutableListOf<String>(currentQuestion.A!!,
            currentQuestion.B!!, currentQuestion.C!!, currentQuestion.D!!)
        choices.shuffle()
    }

    fun handleChoiceSelection(){
        // Get radio group selected item using on checked change listener
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            Toast.makeText(this, "Selected : ${radio.text}", Toast.LENGTH_SHORT).show()
        }
    }
}
