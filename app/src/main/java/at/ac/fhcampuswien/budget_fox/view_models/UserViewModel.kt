package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private var _newUser = mutableStateOf(value = true).value
    val newUser: Boolean
        get() = _newUser

    fun setUserState(firstLogin: Boolean?) {
        _newUser = firstLogin ?: return
    }
}