package com.luke.luke_blog

import java.util.*

class TestTime {
}

fun main() {
    val currentTimeMills = System.currentTimeMillis()
    val calendar = Calendar.getInstance()
    calendar.set(2999, 11, 1)
    val timeInMillis = calendar.timeInMillis
    val date = Date()
    println(timeInMillis)
}