package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class StatisticsViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private var _incomes = MutableStateFlow(mutableMapOf<Int, Double>())
    val incomes: StateFlow<Map<Int, Double>>
        get() = _incomes.asStateFlow()

    private var _expenses = MutableStateFlow(mutableMapOf<Int, Double>())
    val expenses: StateFlow<Map<Int, Double>>
        get() = _expenses.asStateFlow()

    fun mapTransactionsFromUserToMonths(
        year: Int
    ) {
        val userId = Firebase.auth.currentUser?.uid ?: ""

        userRepository.getTransactionsFromUser(
            userId = userId,
            onSuccess = { transactions ->
                for (month in 0..11) {
                    _incomes.value[month] = 0.0
                    _expenses.value[month] = 0.0
                }
                transactions.forEach { transaction ->
                    if (transaction.date.year + 1900 == year) { // adding 1900 because years since 1900 are returned
                        if (transaction.amount > 0) {
                            _incomes.value[transaction.date.month] =
                                _incomes.value[transaction.date.month]!! + transaction.amount
                        } else {
                            _expenses.value[transaction.date.month] =
                                _expenses.value[transaction.date.month]!! - transaction.amount
                        }
                    }
                }
            },
            onFailure = { exception ->

            })
    }

    init {
        mapTransactionsFromUserToMonths(Calendar.getInstance().get(Calendar.YEAR))
    }
}
