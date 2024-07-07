package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Budget
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

class BudgetViewModel : ViewModel() {
    private val repository = Repository()

    private val _budgets = MutableStateFlow<List<Budget>>(mutableListOf())
    val budgets: MutableStateFlow<List<Budget>>
        get() = _budgets

    fun loadCategories(userId: String) {
        val newBudgets = mutableListOf<Budget>()
        repository.getCategoriesFromUser(userId) { categories ->
            categories.forEach { category ->
                if (category.budgetPerMonth > 0.0) {
                    val budget =
                        Budget(description = category.name, budget = category.budgetPerMonth)
                    category.transactionMemberships.forEach { transactionUid ->
                        repository.getSpecificTransaction(userId, transactionUid) { transaction ->
                            if (transaction.date.month + 1 == LocalDateTime.now().monthValue &&
                                transaction.date.year + 1900 == LocalDateTime.now().year &&
                                transaction.amount < 0
                            ) {
                                budget.amount += transaction.amount * -1
                            }
                        }
                    }
                    newBudgets.add(budget)
                }
            }
            _budgets.value = newBudgets
        }
    }
}