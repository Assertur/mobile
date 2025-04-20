package ca.uqac.tp_mobile.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRA MISU) {
        val mainChannel = NotificationChannel(
            "Main channel ID", "Main channel", NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(mainChannel)
        val highPriorityChannel = NotificationChannel(
            "High priority routine channel ID",
            "High priority routine channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(highPriorityChannel)
        val mediumPriorityChannel = NotificationChannel(
            "Medium priority routine channel ID",
            "Medium priority routine channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(mediumPriorityChannel)
        val lowPriorityChannel = NotificationChannel(
            "Low priority routine channel ID",
            "Low priority routine channel",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(lowPriorityChannel)
//        }

        return notificationManager
    }

    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context,
        channelId: String,
        title: String,
        contentText: String,
        smallIcon: Int = android.R.drawable.ic_notification_overlay
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSmallIcon(smallIcon)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }
}