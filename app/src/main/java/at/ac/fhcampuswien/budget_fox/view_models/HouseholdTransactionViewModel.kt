package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Household
import at.ac.fhcampuswien.budget_fox.models.Transaction

class HouseholdTransactionViewModel : ViewModel() {
    private val userRepository = Repository()

    val numbersVisible = mutableStateOf(value = true)

    private var household: Household? = null

    fun getHousehold(householdId: String) {
        userRepository.getHousehold(householdId, onSuccess= { household ->
            if(household != null)
            {
                userRepository.getTransactionsFromHousehold(householdId, onSuccess = { transactions ->
                    transactions.forEach {
                            transaction -> household.addTransaction(transaction)
                    }
                    this.household = household
                })
            }
        })
    }

    fun getTransactions(): List<Transaction> {
        return household?.getTransactions() ?: mutableListOf<Transaction>()
    }
}