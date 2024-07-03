package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Default)
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

    /*
    fun getUserData(userId: String): StateFlow<User?> {
        return repository.getData(userId = userId)
            .stateIn(
                initialValue = null,
                started = SharingStarted.Lazily,
                scope = scope
            )
    }
     */
}