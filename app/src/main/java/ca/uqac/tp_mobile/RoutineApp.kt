package ca.uqac.tp_mobile

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoutineApp  : Application(){
    override fun onCreate() {
        super.onCreate()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mainChannel = NotificationChannel(
            "Main channel ID", "Main channel", NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(mainChannel)
    }
}
