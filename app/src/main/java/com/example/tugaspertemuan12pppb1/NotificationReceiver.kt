package com.example.tugaspertemuan12pppb1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        when (intent.action) {
            "LIKE_ACTION" -> {
                Toast.makeText(context, "Liked!", Toast.LENGTH_SHORT).show()
            }
            "DISLIKE_ACTION" -> {
                Toast.makeText(context, "Disliked!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
