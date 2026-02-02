package com.example.lab.domain

import com.google.firebase.Timestamp

data class HistoryItem(
    val expression: String,
    val result: String,
    val timestamp: Timestamp = Timestamp.now()
)