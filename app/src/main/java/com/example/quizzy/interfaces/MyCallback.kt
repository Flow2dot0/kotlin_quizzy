package com.example.quizzy.interfaces

import com.example.quizzy.models.MyQuestion

interface MyCallback {
    fun onCallback(value: List<MyQuestion>)
}