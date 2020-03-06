package com.example.quizzy.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.quizzy.R
import com.example.quizzy.models.MyManager
import kotlinx.android.synthetic.main.activity_full_screen_image.*

class FullScreenImageActivity : AppCompatActivity() {

    companion object {
        val TAG = "FullScreenImageActivity"
    }

    var manager = MyManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        manager.retrieveDataFromNavigate(intent, TAG)

        val imageView = ImageView(this)
        Glide.with(this).load(manager.pathImageToFullScreen).into(imageView)

        imageViewFull.addView(imageView)
    }
}
