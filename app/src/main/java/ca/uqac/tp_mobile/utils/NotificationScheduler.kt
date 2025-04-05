package ca.uqac.tp_mobile.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import ca.uqac.tp_mobile.presentation.Day
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

        val routineTime = routine.hour.split(":")
        val targetDays = Day.toIds(routine.day) // Liste des jours cibles (par exemple, [1, 3, 5] pour lundi, mercredi, vendredi)

        targetDays.forEach { targetDay ->
            // Création du calendrier pour chaque jour de la semaine
            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, routineTime.first().toInt())
                set(Calendar.MINUTE, routineTime.last().toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                // Calcule la différence de jours pour que l'alarme soit dans le futur
                var diff = (targetDay - get(Calendar.DAY_OF_WEEK) + 7) % 7
                if (diff == 0 && after(this)) {
                    diff = 7 // Si l'heure est déjà passée aujourd'hui, on programme pour la semaine suivante
                }

                add(Calendar.DAY_OF_YEAR, diff) // On ajuste la date
            }

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                )
                // Pour faire une répétition hebdomadaire, on programme la notification pour chaque semaine
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7, // Répétition chaque semaine
                    pendingIntent
                )
            } catch (e: SecurityException) {
                Log.d(null, "Can't schedule alarm, ${e.message}")
            }
        }
    }
}