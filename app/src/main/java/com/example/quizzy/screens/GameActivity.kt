package com.example.quizzy.screens

import android.os.Bundle
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
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    private val TAG = "Game Activity"
    private val RECOVERY_DIALOG_REQUEST = 1

    private val statusList = mutableListOf<String>("START", "REFRESHED", "COMPLETED", "FINAL")
    private val status = statusList[0]

    private val manager = MyManager()
    lateinit var currentQuestion : MyQuestion
    lateinit var choices : MutableList<String>
    lateinit var path : String
    lateinit var correct : String
    lateinit var score: MyScore
    var userAnswer by Delegates.notNull<Boolean>()
    lateinit var youTubePlayerFragment : YouTubePlayerSupportFragment



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        manager.retrieveDataFromNavigate(intent)
        youTubePlayerFragment = (supportFragmentManager.findFragmentById(R.id.third_party_player_view) as YouTubePlayerSupportFragment?)!!
        youTubePlayerFragment.initialize("AIzaSyB5hIzmoI7JANpXYWQLm4liboActq_VXUQ", this)
        updateUI()
        handleUserSelection()
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

    fun handleUserSelection(){
        nextButton.setOnClickListener {
            var id: Int = radioGroup.checkedRadioButtonId
            if(id!=-1){
                val radio : RadioButton = findViewById(id)
                if(radio.text == correct){
                    // TODO : change background color to GREEN
                    userAnswer = true
                    Toast.makeText(this,"Correct :" +
                            " ${radio.text}",
                        Toast.LENGTH_LONG).show()
                }else{
                    // TODO : change background color to RED
                    userAnswer = false
                    Toast.makeText(this,"False :" +
                            " ${correct}",
                        Toast.LENGTH_LONG).show()
                }
                runBlocking {
                    delay(2000)
                    nextButton.setText("NEXT")
                }

                nextButton.setOnClickListener {
                    // TODO : add question index number answer TRUE|FALSE on score

                    when(manager.indexQuestion){
                        0 -> score.first = userAnswer
                        1 -> score.second = userAnswer
                        2 -> score.third = userAnswer
                        3 -> score.fourth = userAnswer
                        4 -> score.fourth = userAnswer
                    }

                    if(manager.indexQuestion != 4 || manager.indexQuestion >= 0){
                        // TODO : increment question
                        // TODO : update UI
                        manager.indexQuestion++
                        updateUI()
                        handleUserSelection()
                    }else{
                        // TODO : at end of list question navigate to Results Activity
                        manager.navigateToResultsWithData(this)
                        // TODO : then after 3 seconds navigate to HomeActivity
                    }
                }


            }else{
                Toast.makeText(this,"Something wrong :" +
                        " nothing selected",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateUI(){
        currentQuestion = manager.currentListQuestions[manager.indexQuestion]
        correct = currentQuestion.answer.toString()
        path = currentQuestion.path.toString()
        score = manager.currentScore
        nextButton.text = "OK"
        val qNumber = "${manager.indexQuestion+1} / 5"
        statusQuestionNumber.text = qNumber
        // init yt player
        youTubePlayerFragment.onDestroy()
        youTubePlayerFragment.initialize("AIzaSyB5hIzmoI7JANpXYWQLm4liboActq_VXUQ", this)
        // TODO : display value for each radio button
        questionTitle.setText("${currentQuestion.title}")
        randomiseChoices()
        radioButton.setText(choices[0])
        radioButton2.setText(choices[1])
        radioButton3.setText(choices[2])
        radioButton4.setText(choices[3])
    }



}


