package com.example.quizzy.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.R
import com.example.quizzy.models.MyManager
import kotlinx.android.synthetic.main.activity_game.*
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class GameActivity : AppCompatActivity(), YouTubePlayer.OnInitializedListener {

    val manager = MyManager()

    private val RECOVERY_DIALOG_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var isDone = View.INVISIBLE

        card2.visibility = isDone
        val youTubePlayerFragment = supportFragmentManager.findFragmentById(R.id.third_party_player_view) as YouTubePlayerSupportFragment?
        youTubePlayerFragment?.initialize("AIzaSyB5hIzmoI7JANpXYWQLm4liboActq_VXUQ", this)

        // TODO : display value for each radio button





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
            youTubePlayer.cueVideo("-WzHcCfZwRs")
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
}
