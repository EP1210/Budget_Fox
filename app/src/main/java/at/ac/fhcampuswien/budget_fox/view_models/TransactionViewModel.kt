package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Transaction
import java.text.SimpleDateFormat
import java.util.Date

class TransactionViewModel : ViewModel() {

    private val repository = Repository()

    private var _transactionDate = mutableStateOf(value = "").value
    val transactionDate: String
        get() = _transactionDate

    private var _transactionAmount = mutableDoubleStateOf(value = 0.0).doubleValue
    val transactionAmount: Double
        get() = _transactionAmount

    private var _transactionDescription = mutableStateOf(value = "").value
    val transactionDescription: String
        get() = _transactionDescription

    var transactionMessage = mutableStateOf(value = "")
        private set

    fun setTransactionDate(date: String) {
        _transactionDate = date
    }

    fun setTransactionAmount(amount: Double) {
        _transactionAmount = amount
    }

    fun setTransactionDescription(description: String) {
        _transactionDescription = description
    }

    fun insertTransaction(userId: String) {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date: Date? = format.parse(transactionDate)

        if (transactionAmount != 0.0 && transactionDescription.isNotBlank() && date != null) {
            val transaction = Transaction(
                amount = transactionAmount,
                description = transactionDescription,
                date = date
            )

            repository.insertTransaction(
                transaction = transaction,
                userId = userId,
                onSuccess = {
                    transactionMessage.value = "Transaction added successfully!"
                },
                onFailure = {
                    transactionMessage.value = "Error adding transaction: ${it.message}."
                }
            )
        } else {
            transactionMessage.value = "Invalid transaction data."
        }
    }
}