package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository

class HouseholdSettingsViewModel : ViewModel() {
    private val repository = Repository()

    private var _size = mutableStateOf(value = IntSize.Zero)

    val size: MutableState<IntSize>
        get() = _size

    fun setSize(value: IntSize) {
        _size.value = value
    }

    fun leaveHousehold(userId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        repository.getAllDataFromUser(userId = userId, onSuccess = { user ->
            if (user != null) {
                user.householdId = ""
                repository.insertUser(user = user, userId = userId, onSuccess = onSuccess)
            }
        }, onFailure = {
            onFailure("User not found!")
        })
    }
}