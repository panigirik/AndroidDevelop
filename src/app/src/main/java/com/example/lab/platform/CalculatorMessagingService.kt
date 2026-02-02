package com.example.lab.platform

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CalculatorMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title ?: "Calculator"
        val body = message.notification?.body ?: "New message"

        Log.d("FCM", "Push received: $title - $body")
    }
}