package com.lambao.ttsdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.lambao.service.TTSBackgroundService

class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("lamnb", "onMessageReceived: ${message.data}")
        val title = message.notification?.title ?: message.data["title"] ?: "Default Title"
        val body = message.notification?.body ?: message.data["body"] ?: "Default Body"

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "my_channel_id" // Replace with your channel ID

        // Create notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, "My Channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(Notification.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your icon
            .setAutoCancel(true)

        // Display the notification
        notificationManager.notify(1, notificationBuilder.build())

        if (body.isNotEmpty()) {
            val intent = Intent(this, TTSBackgroundService::class.java).apply {
                putExtra("textToSpeak", body)
            }
            Log.d("lamnb", "start service")
            startService(intent) // Start as a background service
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}