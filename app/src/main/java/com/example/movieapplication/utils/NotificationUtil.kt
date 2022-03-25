package com.example.movieapplication.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.movieapplication.R
import java.util.*

object NotificationUtil {
    private var NOTIFICATION_ID = 0
    private const val CHANNEL_ID = "MY_CHANNEL_ID"
    const val CHANNEL_NAME = "Upax Channel"

    fun notify(title: String, messageBody: String, applicationContext: Context) {
        val def = Notification.DEFAULT_ALL
        val notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        val builder = NotificationCompat.Builder(
            applicationContext,
            CHANNEL_ID
        )
        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.addLine(messageBody)

        val pattern = longArrayOf(0, 200, 60, 200)
        builder
            .setAutoCancel(true)
            .setContentTitle(title)
            .setDefaults(def)
            .setStyle(inboxStyle)
            .setWhen(Date().time)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setVibrate(pattern)
            .setContentText(messageBody)
            .setChannelId(CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.priority = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.description = "Main Notifications"
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = pattern
            notificationManager.createNotificationChannel(notificationChannel)
        }
        NOTIFICATION_ID = Random().nextInt(100 - 1) + 1
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}