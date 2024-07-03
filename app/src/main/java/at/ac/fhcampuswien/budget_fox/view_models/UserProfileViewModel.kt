package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _user = MutableStateFlow<User?>(null)

    fun getUserData(userId: String): StateFlow<User?> {
        viewModelScope.launch {
            repository.getAllDataFromUser(userId = userId, onSuccess = { user ->
                _user.value = user
            }, onFailure = {

            })
        }
        return _user
    }
}