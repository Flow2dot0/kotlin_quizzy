package com.example.quizzy

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.interfaces.MyCallback
import com.example.quizzy.models.MyManager
import com.example.quizzy.models.MyQuestion
import com.example.quizzy.screens.CreditsActivity
import com.example.quizzy.screens.GameActivity
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    val TAG = "HOME"
    val manager = MyManager()
    lateinit var questionsData : List<MyQuestion>

    val levels =
        arrayOf(
            Html.fromHtml("<font color='#ffffff'>Newbie</font>"),
            Html.fromHtml("<font color='#ffffff'>Between</font>"),
            Html.fromHtml("<font color='#ffffff'>GOD</font>"),
            Html.fromHtml("<font color='#ffffff'>TITAN</font>"))

    var selectedLevelIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        playButton.setOnClickListener {
            showAlertDialog()

        }


        aboutButton.setOnClickListener {
            val intent = Intent(this, CreditsActivity::class.java)
            startActivity(intent)
        }
    }

    // Alert with selected level options, bonus : funny toasts
    private fun showAlertDialog() {
        val alertDialog =
            AlertDialog.Builder(this@HomeActivity ,R.style.AlertDialogStyle)
        alertDialog.setTitle("How strong are you ?")
        alertDialog.setSingleChoiceItems(
            levels,
            selectedLevelIndex
        ) { dialog, which ->
            when (which) {
                0 -> {
                    selectedLevelIndex = which
                    Log.i(TAG, "Current level selected : $selectedLevelIndex")
                    Toast.makeText(
                        this@HomeActivity,
                        "No worries, it will be fine...",
                        Toast.LENGTH_LONG
                    ).show()
                }

                1 -> {
                    selectedLevelIndex = which
                    Log.i(TAG, "Current level selected : $selectedLevelIndex")

                    Toast.makeText(
                        this@HomeActivity,
                        "Let's upgrade a bit the difficulty",
                        Toast.LENGTH_LONG
                    ).show()
                }
                2 -> {
                    selectedLevelIndex = which
                    Log.i(TAG, "Current level selected : $selectedLevelIndex")

                    Toast.makeText(
                        this@HomeActivity,
                        "Are you really one ?",
                        Toast.LENGTH_LONG
                    ).show()

                }
                3 -> {
                    selectedLevelIndex = which
                    Log.i(TAG, "Current level selected : $selectedLevelIndex")

                    Toast.makeText(
                        this@HomeActivity,
                        "At least you're already a GOD, let's see if you might be a TITAN (API Questions) ?",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(true)
        alert.setOnCancelListener {
            loadData()
        }
        alert.show()
    }

    private fun loadData(){
        manager.getListOfQuestionFromDB(object : MyCallback{
            override fun onCallback(value: List<MyQuestion>) {
                questionsData = value
                println("PEUT ETRE MES DATA : $questionsData")
                // TODO : add navigation to Game Activity
                navigateToActivity()
                // TODO : transfer data to Game Activity
            }
        }, selectedLevelIndex)
    }

    private fun navigateToActivity(){
        val intent = Intent(this, GameActivity::class.java)
        var newList : ArrayList<MyQuestion> = questionsData as ArrayList<MyQuestion>
        intent.putParcelableArrayListExtra("data", newList)
        startActivity(intent)
    }

}
