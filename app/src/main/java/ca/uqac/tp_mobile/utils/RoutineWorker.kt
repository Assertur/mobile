package ca.uqac.tp_mobile.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


class RoutineWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val notificationId = inputData.getInt("routineId", 0)
        val title = inputData.getString("routineTitle") ?: "Routine"

        val notificationManager = NotificationManagerCompat.from(applicationContext)

        val builder = NotificationCompat.Builder(applicationContext, "Main channel ID")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle(title)
            .setContentText("Il est temps de faire ta routine ✨")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }
        notificationManager.notify(notificationId, builder.build())

        return Result.success()
    }
}
