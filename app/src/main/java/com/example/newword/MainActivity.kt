package com.example.newword

import android.content.Context
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newword.ui.NewWordScreen
import com.example.newword.ui.theme.NewWordTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewWordTheme {
                val windowSize = calculateWindowSizeClass(this)
                NewWordScreen(windowSize = windowSize.widthSizeClass)
            }
        }
        requestNotificationPermission()
        scheduleNotification(applicationContext)
    }
    private fun scheduleNotification(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "periodic_word_notification",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }
    }

}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun NewWordAppCompactPreview() {
    NewWordTheme {
        NewWordScreen(windowSize = WindowWidthSizeClass.Compact)
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 480)
@Composable
fun NewWordAppMediumPreview() {
    NewWordTheme {
        NewWordScreen(windowSize = WindowWidthSizeClass.Medium)
    }
}

@Preview(showBackground = true, widthDp = 840, heightDp = 480)
@Composable
fun NewWordAppExpandedPreview() {
    NewWordTheme {
        NewWordScreen(windowSize = WindowWidthSizeClass.Expanded)
    }
}