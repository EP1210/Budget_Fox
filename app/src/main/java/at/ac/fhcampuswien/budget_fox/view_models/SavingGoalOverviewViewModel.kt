package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.SavingGoal
import kotlinx.coroutines.flow.MutableStateFlow

class SavingGoalOverviewViewModel : ViewModel() {
    private val userRepository = Repository()

    private var _bottomSheetVisible: MutableState<Boolean> = mutableStateOf(false)
    val bottomSheetVisible: MutableState<Boolean>
        get() = _bottomSheetVisible

    fun setBottomSheetVisible(visible: Boolean) {
        _bottomSheetVisible.value = visible
    }

    private var _goal: MutableState<SavingGoal?> = mutableStateOf(value = null)
    val selectedGoal: MutableState<SavingGoal?>
        get() = _goal

    private var _selectedName: String = ""
    private var _amount: Double = 0.0

    fun setGoalName(name: String) {
        _selectedName = name
    }

    fun setGoalAmount(amount: Double) {
        _amount = amount
    }

    private val _goals = MutableStateFlow<List<SavingGoal>>(mutableListOf())

    fun loadSavingGoals(userId: String): MutableStateFlow<List<SavingGoal>> {
        userRepository.getSavingGoalsFromUser(userId, onSuccess = { goals ->
            _goals.value = goals
        })
        return _goals
    }

    fun loadSelectedGoal(goalId: String) {
        _goal.value = _goals.value.find { savingGoal ->
            savingGoal.uuid == goalId
        }
        _amount = _goal.value?.amount ?: 0.0
        _selectedName = _goal.value?.name ?: ""
    }

    fun saveEditedGoal(userId: String, onSuccess: () -> Unit) {
        if (_goal.value != null) {
            _goal.value?.name = _selectedName
            _goal.value?.amount = _amount
            userRepository.savingGoalToDatabase(userId = userId, savingGoal = _goal.value!!) {
                onSuccess()
            }
        }


    }
}