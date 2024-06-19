package at.ac.fhcampuswien.budget_fox.worker

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import at.ac.fhcampuswien.budget_fox.models.Transaction
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.firestore
import java.util.Calendar
import java.util.Date

class RegularExpenseWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val db = Firebase.firestore
        val currentTime = Date()

        db.collection("transactions")
            .whereEqualTo("isRegular", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val transactionData = document.data
                    val transaction = Transaction.fromDatabase(transactionData)
                    if (transaction.nextDueDate != null && transaction.nextDueDate!!.before(currentTime)) {
                        val newTransaction = Transaction(
                            amount = transaction.amount,
                            description = transaction.description,
                            date = Date(),
                            isRegular = transaction.isRegular,
                            frequency = transaction.frequency,
                            nextDueDate = calculateNextDueDate(Date(), transaction.frequency!!)
                        )
                        insertNewTransaction(newTransaction)
                        db.collection("transactions").document(document.id)
                            .update("nextDueDate", newTransaction.nextDueDate?.let { Timestamp(it) })
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting documents", e)
            }

        return Result.success()
    }

    private fun calculateNextDueDate(currentDate: Date, frequency: String): Date {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        when (frequency) {
            "daily" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            "weekly" -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
            "monthly" -> calendar.add(Calendar.MONTH, 1)
            "yearly" -> calendar.add(Calendar.YEAR, 1)
        }
        return calendar.time
    }

    private fun insertNewTransaction(transaction: Transaction) {
        val db = Firebase.firestore
        db.collection("transactions")
            .add(transaction.transactionToDatabase())
            .addOnSuccessListener {
                Log.d(TAG, "Regular transaction added with ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding regular transaction", e)
            }
    }
}

