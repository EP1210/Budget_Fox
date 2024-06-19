package at.ac.fhcampuswien.budget_fox

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import at.ac.fhcampuswien.budget_fox.navigation.Navigation
import at.ac.fhcampuswien.budget_fox.ui.theme.Budget_FoxTheme
import at.ac.fhcampuswien.budget_fox.worker.RegularExpenseWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleRegularExpenseWorker(this)

        setContent {
            Budget_FoxTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }

    private fun scheduleRegularExpenseWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<RegularExpenseWorker>(1, TimeUnit.DAYS)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "RegularExpenseWorker",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

}