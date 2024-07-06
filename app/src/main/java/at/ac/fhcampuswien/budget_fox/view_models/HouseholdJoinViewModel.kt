package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository

class HouseholdJoinViewModel : ViewModel() {
    private val repository = Repository()

    private var _userMessage = mutableStateOf(value = "Scan QR-Code")
    val userMessage: MutableState<String>
        get() = _userMessage


    fun joinHousehold(householdId: String, userId: String, onSuccess: (String) -> Unit = {}) {
        repository.joinHouseholdIfExist(
            userId = userId,
            householdId = householdId,
            onSuccess = {
                onSuccess(householdId)
            },
            notExits = {
                _userMessage.value = "Scanned household does not exist!"
            }
        )
    }
}