package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class StatisticsViewModel : ViewModel() {

    private val userRepository = Repository()

    private var _incomes = MutableStateFlow(mutableMapOf<Int, Double>())
    val incomes: StateFlow<Map<Int, Double>>
        get() = _incomes.asStateFlow()

    private var _expenses = MutableStateFlow(mutableMapOf<Int, Double>())
    val expenses: StateFlow<Map<Int, Double>>
        get() = _expenses.asStateFlow()

    private var _selectedYear = MutableStateFlow(Calendar.getInstance().get(Calendar.YEAR))
    val selectedYear: StateFlow<Int>
        get() = _selectedYear.asStateFlow()

    fun setSelectedYear(year: Int, userId: String) {
        _selectedYear.value = year
        mapTransactionsFromUserToMonths(year, userId)
    }

    fun mapTransactionsFromUserToMonths(
        year: Int,
        userId: String,
        onSuccess: () -> Unit = {}
    ) {
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
                onSuccess()
            },
            onFailure = { exception ->

            })
    }

}
