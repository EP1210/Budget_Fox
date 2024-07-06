package at.ac.fhcampuswien.budget_fox.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Transaction

class TransactionListViewModel : ViewModel() {

    private val repository = Repository()

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
        repository.getCategoriesFromUser(userId = userId) { fetchedCategories ->
            fetchedCategories.forEach { category ->
                category.transactionMemberships.remove(transaction.uuid)
                repository.updateCategoryTransactionMemberships(userId = userId, category = category)
            }
        }
        repository.deleteTransaction(userId, transaction.uuid) {
            _transactions.remove(transaction)
        }
    }
}