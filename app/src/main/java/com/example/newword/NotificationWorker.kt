package com.example.newword
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        createNotificationChannel(applicationContext)
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, "word_channel")
            .setContentTitle("Өнөөдрийн үг")
            .setContentText("Шинэ үгээ шалгаарай!")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val manager = NotificationManagerCompat.from(applicationContext)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            NotificationManagerCompat.from(applicationContext).areNotificationsEnabled()
        ) {
            try {
                manager.notify(1, notification)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Word Reminder"
            val description = "Үг тогтоох сануулга"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("word_channel", name, importance).apply {
                this.description = description
            }

            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
