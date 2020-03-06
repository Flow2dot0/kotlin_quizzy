package com.example.quizzy.interfaces

import com.example.quizzy.models.MyQuestion
import com.example.quizzy.models.MyScore

interface MyCallback {
	fun onCallback(value: List<MyQuestion>)
}

interface MyCallbackScore {
	fun onScoreReady(value: List<MyScore>)
}