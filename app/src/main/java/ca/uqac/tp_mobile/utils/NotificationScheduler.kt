package ca.uqac.tp_mobile.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import ca.uqac.tp_mobile.presentation.RoutineVM
import ca.uqac.tp_mobile.receiver.NotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun scheduleRoutineNotification(routine: RoutineVM) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("routineId", routine.id)
            putExtra("message", "C'est l'heure de la routine : ${routine.title}")
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            routine.id,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            val routineTime = routine.hour.split(":")
            set(Calendar.HOUR_OF_DAY, routineTime.first().toInt())
            set(Calendar.MINUTE, routineTime.last().toInt())
            // TODO : schedule pour le day
        }

        // Si l'heure programmée est déjà passée pour aujourd'hui, on ajoute un jour
//        if (calendar.timeInMillis <= System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_MONTH, 1)
//        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
            )
        } catch (e: SecurityException) {
            Log.d(null, "Can't schedule alarm, ${e.message}")
        }

    }
}