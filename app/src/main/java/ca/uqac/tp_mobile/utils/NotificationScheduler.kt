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
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun scheduleRoutineNotification(routine: RoutineVM) {
        val baseIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("routineId", routine.id)
            putExtra("message", "C'est l'heure de la routine : ${routine.title}")
        }
        Log.d("Notification","Création des notifications de la routine ${routine.id}")

        val routineTime = routine.hour.split(":")
        val targetDays = Day.toIds(routine.day) // Liste des jours cibles (par exemple, [1, 3, 5] pour lundi, mercredi, vendredi)

        targetDays.forEach { targetDay ->
            Log.d("Notification","Création de notification pour le jour $targetDay")
            // Création du calendrier pour chaque jour de la semaine
            val baseCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, routineTime.first().toInt())
                set(Calendar.MINUTE, routineTime.last().toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                // Calcule la différence de jours pour que l'alarme soit dans le futur
                var diff = (targetDay + 1 - get(Calendar.DAY_OF_WEEK) + 7) % 7 //calendar.DAY_OF_WEEK commence par le dimanche
                if (diff == 0 && after(this)) {
                    diff = 7 // Si l'heure est déjà passée aujourd'hui, on programme pour la semaine suivante
                }

                add(Calendar.DAY_OF_YEAR, diff) // On ajuste la date
            }
            println("calendrier : ${baseCalendar.time}")
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            // 🔔 Notification principale
            createAlarm(
                calendar = baseCalendar,
                alarmManager = alarmManager,
                intent = baseIntent,
                requestCode = routine.id, // important : un ID unique pour ne pas écraser
            )

            // 🔔 Rappels supplémentaires
            routine.reminders.forEachIndexed { index, reminder ->
                val reminderCalendar = baseCalendar.clone() as Calendar
                reminderCalendar.add(Calendar.DAY_OF_YEAR, -reminder.days)
                reminderCalendar.add(Calendar.HOUR_OF_DAY, -reminder.hours)
                reminderCalendar.add(Calendar.MINUTE, -reminder.minutes)

                val reminderIntent = Intent(context, NotificationReceiver::class.java).apply {
                    putExtra("routineId", routine.id)
                    putExtra(
                        "message",
                        "Rappel : routine '${routine.title}' dans ${reminder.days}j ${reminder.hours}h ${reminder.minutes}min"
                    )
                    action = "reminder_${routine.id}_$index"
                }

                // Pour que chaque rappel ait un ID unique, tu peux faire :
                val reminderRequestCode = routine.id * 100 + index

                val localCalendar = Calendar.getInstance()

                if (reminderCalendar.timeInMillis > localCalendar.timeInMillis) {
                    Log.d("Reminder","Création de notification pour le rappel $reminder")
                    createAlarm(
                        calendar = reminderCalendar,
                        alarmManager = alarmManager,
                        intent = reminderIntent,
                        requestCode = reminderRequestCode
                    )
                } else {
                    Log.d("Reminder", "Rappel ignoré car dans le passé pour ${routine.title}")
                }
            }

        }
    }

    fun cancelRoutineNotification(routineId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Annule la notif principale
        cancelAlarm(routineId, alarmManager)

        // Annule tous les rappels potentiels (de 0 à 9, soit 5 rappels max)
        for (i in 0 until 5) {
            cancelAlarm(routineId * 100 + i, alarmManager)
        }
    }

    private fun cancelAlarm(requestCode: Int, alarmManager: AlarmManager) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        try {
            alarmManager.cancel(pendingIntent)
        } catch (e: SecurityException) {
            Log.d(null, "Can't cancel alarm $requestCode, ${e.message}")
        }
    }


    private fun createAlarm(
        calendar: Calendar,
        alarmManager: AlarmManager,
        intent: Intent,
        requestCode: Int
    ) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            else PendingIntent.FLAG_UPDATE_CURRENT
        )

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
            )
        } catch (e: SecurityException) {
            Log.d(null, "Can't schedule alarm, ${e.message}")
        }
    }
}