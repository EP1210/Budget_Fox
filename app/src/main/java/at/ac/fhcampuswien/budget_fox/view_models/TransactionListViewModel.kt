package at.ac.fhcampuswien.budget_fox.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Transaction

class TransactionListViewModel : ViewModel() {

    private val repository = UserRepository()

    private val _transactions = mutableStateListOf<Transaction>()
    val transactions: List<Transaction>
        get() = _transactions

    val numbersVisible = mutableStateOf(value = true)

    fun loadTransactions(userId: String) {
        repository.getTransactionsFromUser(userId, { fetchedTransactions ->
            _transactions.clear()
            _transactions.addAll(fetchedTransactions.sortedBy { it.date })
        }, { exception ->
            Log.w("UserViewModel", "Error loading transactions: ", exception)
        })
    }

    fun deleteTransaction(userId: String, transaction: Transaction) {
        repository.deleteTransaction(userId, transaction.uuid) {
            _transactions.remove(transaction)
        }
    }
}