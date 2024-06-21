package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Household
import at.ac.fhcampuswien.budget_fox.models.Transaction
import java.text.SimpleDateFormat
import java.util.Date

class HouseholdTransactionAddViewModel: ViewModel() {
    val userRepository = UserRepository()

    private var _household : Household? = mutableStateOf(value = null).value

    private var _transactionDate = mutableStateOf(value = "").value
    val transactionDate: String
        get() = _transactionDate

    private var _transactionAmount = mutableDoubleStateOf(value = 0.0).doubleValue
    val transactionAmount: Double
        get() = _transactionAmount

    private var _transactionDescription = mutableStateOf(value = "").value
    val transactionDescription: String
        get() = _transactionDescription

    fun setTransactionDate(date: String) {
        _transactionDate = date
    }

    fun setTransactionAmount(amount: Double) {
        _transactionAmount = amount
    }

    fun setTransactionDescription(description: String) {
        _transactionDescription = description
    }

    fun findHouseholdById(householdId: String) {
        userRepository.getHousehold(householdId, onSuccess = {
            _household = it
        })
    }

    fun addHouseholdTransaction() {
        if(_household != null) {
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date: Date? = format.parse(transactionDate)

            date?.let {
                val transaction = Transaction(
                    amount = transactionAmount,
                    description = transactionDescription,
                    date = it
                )

                userRepository.insertHouseholdTransaction(
                    transaction = transaction,
                    householdId = _household!!.uuid
                )
                _household!!.addTransaction(transaction)
            }
            //TODO: Display error message
        }
    }
}
