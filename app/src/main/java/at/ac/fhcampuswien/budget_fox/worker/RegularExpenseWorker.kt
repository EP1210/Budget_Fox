package at.ac.fhcampuswien.budget_fox.worker

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Worker
import androidx.work.WorkerParameters
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class RegularExpenseWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val db = Firebase.firestore
        val currentTime = Date()
        val firebaseUser = Firebase.auth.currentUser

        firebaseUser?.uid?.let { userId ->
            db.collection("users").document(userId)
                .collection("transactions")
                .whereEqualTo("isRegular", true)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val transactionData = document.data
                        val transaction = Transaction.fromDatabase(transactionData)
                        if (transaction.nextDueDate != null && transaction.nextDueDate!!.before(currentTime)) {
                            var nextDueDate = transaction.nextDueDate!!

                            while (nextDueDate.before(currentTime)) {
                                val newTransaction = Transaction(
                                    amount = transaction.amount,
                                    description = transaction.description,
                                    date = nextDueDate,
                                    isRegular = false
                                )
                                newTransaction.uuid = UUID.randomUUID().toString()
                                insertNewTransaction(newTransaction, userId)

                                nextDueDate = calculateNextDueDate(nextDueDate, transaction.frequency!!)
                            }

                            db.collection("users").document(userId)
                                .collection("transactions").document(document.id)
                                .update("nextDueDate", nextDueDate.let { Timestamp(it) })
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error getting documents", e)
                }
        }
            return Result.success()
    }

    private fun calculateNextDueDate(currentDate: Date, frequency: String): Date {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        when (frequency.lowercase(Locale.ROOT)) {
            "daily" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            "weekly" -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
            "monthly" -> calendar.add(Calendar.MONTH, 1)
            "yearly" -> calendar.add(Calendar.YEAR, 1)
        }
        return calendar.time
    }

    private fun insertNewTransaction(transaction: Transaction, userId: String) {
        val db = Firebase.firestore
        db.collection("users").document(userId)
            .collection("transactions")
            .document(transaction.uuid)
            .set(transaction.transactionToDatabase())
            .addOnSuccessListener {
                Log.d(TAG, "Regular transaction added with ID: ${transaction.uuid}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding regular transaction", e)
            }
    }

}

