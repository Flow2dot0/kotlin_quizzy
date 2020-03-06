package com.example.quizzy.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizzy.BuildConfig
import com.example.quizzy.R
import kotlinx.android.synthetic.main.activity_credits.*


class CreditsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)


        val versionName = "Version ${BuildConfig.VERSION_NAME.toString()}"


        creditsVersioning.text = versionName

    }
}
