package ca.uqac.tp_mobile.receiver

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val message = intent.getStringExtra("message") ?: "Routine à lancer"
        val notificationId = intent.getIntExtra("routineId", System.currentTimeMillis().toInt())

        val builder = NotificationCompat.Builder(context, "High priority routine channel ID")
            .setSmallIcon(android.R.drawable.ic_notification_overlay).setContentTitle("Routine")
            .setContentText(message).setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }
}