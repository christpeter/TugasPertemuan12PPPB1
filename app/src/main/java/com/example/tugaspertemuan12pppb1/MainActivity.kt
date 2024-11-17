package com.example.tugaspertemuan12pppb1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tugaspertemuan12pppb1.R

class MainActivity : AppCompatActivity() {

    private lateinit var likeCountText: TextView
    private lateinit var dislikeCountText: TextView
    private var likeCount = 0
    private var dislikeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        likeCountText = findViewById(R.id.likeCount)
        dislikeCountText = findViewById(R.id.dislikeCount)

        val likeIcon = findViewById<ImageView>(R.id.likeIcon)
        val dislikeIcon = findViewById<ImageView>(R.id.dislikeIcon)

        likeIcon.setOnClickListener {
            likeCount++
            updateCounts()
        }

        dislikeIcon.setOnClickListener {
            dislikeCount++
            updateCounts()
        }

        createNotificationChannel()
        sendNotification()
    }

    private fun updateCounts() {
        likeCountText.text = likeCount.toString()
        dislikeCountText.text = dislikeCount.toString()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "demo_channel",
                "Demo Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification() {
        val likeIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = "LIKE_ACTION"
        }
        val likePendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            likeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val dislikeIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = "DISLIKE_ACTION"
        }
        val dislikePendingIntent = PendingIntent.getBroadcast(
            this,
            1,
            dislikeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, "demo_channel")
            .setContentTitle("Vote Now!")
            .setContentText("Do you like or dislike?")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(R.drawable.ic_like, "Like", likePendingIntent)
            .addAction(R.drawable.ic_dislike, "Dislike", dislikePendingIntent)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }
}
