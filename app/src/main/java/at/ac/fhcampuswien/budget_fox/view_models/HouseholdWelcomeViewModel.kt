package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.User

class HouseholdWelcomeViewModel : ViewModel() {

    private val repository = Repository()

    private var _householdId = mutableStateOf(value = "")
    val householdId: MutableState<String>
        get() = _householdId

    private fun getUserData(userId: String, onSuccess: (User?) -> Unit) {
        repository.getAllDataFromUser(
            userId = userId,
            onSuccess = onSuccess,
            onFailure = {}
        )
    }

    fun getHousehold(userId: String) {
        getUserData(userId = userId) { user ->
            if (user != null) {
                _householdId.value = user.householdId
            }
        }
    }
}