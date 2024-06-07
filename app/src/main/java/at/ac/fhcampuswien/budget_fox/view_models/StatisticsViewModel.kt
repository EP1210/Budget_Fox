package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class StatisticsViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private var _incomes = mutableMapOf<Int, Double>()
    val incomes: Map<Int, Double>
        get() = _incomes

    private var _expenses = mutableMapOf<Int, Double>()
    val expenses: Map<Int, Double>
        get() = _expenses

    fun mapTransactionsFromUserToMonths(
        year: Int
    ) {
        val userId = Firebase.auth.currentUser?.uid ?: ""

        userRepository.getTransactionsFromUser(
            userId = userId,
            onSuccess = { transactions ->
                for (month in 0..11) {
                    _incomes[month] = 0.0
                    _expenses[month] = 0.0
                }
                transactions.forEach { transaction ->
                    if (transaction.date.year + 1900 == year) { // adding 1900 because years since 1900 are returned
                            if (transaction.amount > 0) {
                                _incomes[transaction.date.month] = _incomes[transaction.date.month]!! + transaction.amount
                            } else {
                                _expenses[transaction.date.month] = _expenses[transaction.date.month]!! - transaction.amount
                            }
                    }
                }
            },
            onFailure = { exception ->

            })
    }

    init {
        mapTransactionsFromUserToMonths(year = 2024)
    }
}
