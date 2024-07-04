package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Transaction
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date


class RegularTransactionViewModel : ViewModel() {
    private val repository = UserRepository()
    private var _transactionMessage = mutableStateOf<String>(value = "")

    val transactionMessage: MutableState<String>
        get() = _transactionMessage

    private var _transactionFrequency = mutableStateOf(value = "Monthly").value
    val transactionFrequency: String
        get() = _transactionFrequency

    private var _transactionAmount = mutableDoubleStateOf(value = 0.0).doubleValue
    val transactionAmount: Double
        get() = _transactionAmount

    private var _transactionDescription = mutableStateOf(value = "").value
    val transactionDescription: String
        get() = _transactionDescription

    private var _nextDueDate = mutableStateOf(value = Date()).value
    val nextDueDate: Date
        get() = _nextDueDate

    private var _transactionDate = mutableStateOf(value = LocalDateTime.now())
    val transactionDate: MutableState<LocalDateTime>
        get() = _transactionDate

    fun setTransactionDate(date: LocalDateTime) {
        _transactionDate.value = date
    }

    fun setTransactionAmount(amount: Double) {
        _transactionAmount = amount
    }

    fun setTransactionFrequency(frequency: String) {
        _transactionFrequency = frequency
        calculateNextDueDate()
    }

    fun setTransactionDescription(description: String) {
        _transactionDescription = description
    }

    private fun calculateNextDueDate() {

        val calendar = Calendar.getInstance()

        //https://stackoverflow.com/a/19726814/21992499
        val instant: java.time.Instant? = transactionDate.value.toInstant(ZoneOffset.UTC)
        calendar.time = Date.from(instant)
        when (_transactionFrequency) {
            "Daily" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            "Weekly" -> calendar.add(Calendar.WEEK_OF_YEAR, 1)
            "Monthly" -> calendar.add(Calendar.MONTH, 1)
            "Yearly" -> calendar.add(Calendar.YEAR, 1)
        }
        _nextDueDate = calendar.time
    }

    fun insertRegularTransaction(userId: String) {
        val instant: java.time.Instant? = transactionDate.value.toInstant(ZoneOffset.UTC)
        val inputDate: Date? = Date.from(instant)

        if (transactionAmount != 0.0 && transactionDescription.isNotBlank() && inputDate != null) {
            val calendar = Calendar.getInstance()
            val currentTime = calendar.time

            val calendarDate = Calendar.getInstance()
            calendarDate.time = inputDate
            val calendarTime = Calendar.getInstance()
            calendarTime.time = currentTime

            calendarDate.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY))
            calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE))
            calendarDate.set(Calendar.SECOND, calendarTime.get(Calendar.SECOND))

            val combinedDateTime = calendarDate.time

            val transaction = Transaction(
                amount = transactionAmount,
                description = transactionDescription,
                date = combinedDateTime,
                isRegular = true,
                frequency = transactionFrequency,
                nextDueDate = nextDueDate
            )

            repository.insertTransaction(
                transaction = transaction,
                userId = userId,
                onSuccess = {
                    transactionMessage.value = "Regular transaction added successfully!"
                },
                onFailure = {
                    transactionMessage.value = "Error adding regular transaction: ${it.message}"
                }
            )
        } else {
            transactionMessage.value = "Invalid regular transaction data."
        }
    }
}