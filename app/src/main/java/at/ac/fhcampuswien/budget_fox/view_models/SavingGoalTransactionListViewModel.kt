package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Transaction

class SavingGoalTransactionListViewModel : ViewModel() {
    private var repository = Repository()

    private var _bottomSheetVisible : MutableState<Boolean> = mutableStateOf(false)
    val bottomSheetVisible : MutableState<Boolean>
        get() = _bottomSheetVisible

    private var _alertVisible : MutableState<Boolean> = mutableStateOf(false)
    val alertVisible : MutableState<Boolean>
        get() = _alertVisible

    private val _transactions = mutableStateListOf<Transaction>()
    val transactions: List<Transaction>
        get() = _transactions

    private var _done : MutableState<Boolean> = mutableStateOf(true)
    val doneState : MutableState<Boolean>
        get() = _done

    fun setBottomSheetVisible(visible: Boolean) {
        _bottomSheetVisible.value = visible
    }

    fun setAlertVisible(visible: Boolean) {
        _alertVisible.value = visible
    }

    private var _amount : Double = 0.0

    fun setAmount(amount: Double) {
        _amount = amount
    }

    fun getTransactions(userId: String, savingGoalId: String) {
        repository.getTransactionsForSpecificSavingGoal(userId, savingGoalId) {
            _transactions.clear()
            _transactions.addAll(it)
        }
    }

    fun transferToSavingGoal(userId: String, savingGoalId: String, onSuccess: () -> Unit) {
        repository.transferToSavingGoal(userId, savingGoalId, _amount) {
            _amount = 0.0
            setBottomSheetVisible(visible = false)
            onSuccess()
        }
    }

    fun getDoneState(userId: String, savingGoalId: String) {
        repository.savingGoalIsDone(userId, savingGoalId) {
            _done.value = it
        }
    }

    fun markSavingGoalAsDone(userId: String, savingGoalId: String, onSuccess: () -> Unit) {
        repository.markSavingGoalAsDone(userId, savingGoalId) {
            _done.value = true
            onSuccess()
        }
    }
}