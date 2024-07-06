package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Household
import at.ac.fhcampuswien.budget_fox.models.Transaction

class HouseholdTransactionViewModel : ViewModel() {
    private val userRepository = Repository()

    val numbersVisible = mutableStateOf(value = true)

    private var _household: MutableState<Household?> = mutableStateOf(value = null)
    val household: MutableState<Household?>
        get() = _household

    fun getHousehold(householdId: String) {
        userRepository.getHousehold(householdId, onSuccess = { household ->
            if (household != null) {
                userRepository.getTransactionsFromHousehold(
                    householdId,
                    onSuccess = { transactions ->
                        transactions.forEach { transaction ->
                            household.addTransaction(transaction)
                        }
                        this._household.value = household
                    })
            }
        })
    }

    fun getTransactions(): List<Transaction> {
        return _household.value?.getTransactions() ?: mutableListOf<Transaction>()
    }
}