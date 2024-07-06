package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.ac.fhcampuswien.budget_fox.data.Repository
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class UserProfileViewModel : ViewModel() {
    private val repository = Repository()

    private var _userName = mutableStateOf(value = "")
    val userName: MutableState<String>
        get() = _userName

    private var _userBirthDate = mutableStateOf(value = LocalDateTime.now())
    val userBirthDate: MutableState<LocalDateTime>
        get() = _userBirthDate

    private var _userRegistrationDate = mutableStateOf(value = LocalDateTime.now())
    val userRegistrationDate: MutableState<LocalDateTime>
        get() = _userRegistrationDate

    fun getUserData(userId: String) {
        viewModelScope.launch {
            repository.getAllDataFromUser(userId = userId, onSuccess = { user ->
                _userName.value = user?.firstName + " " + user?.lastName
                _userBirthDate.value = epochToDate(value = user?.dateOfBirthInEpoch)
                _userRegistrationDate.value = epochToDate(value = user?.dateOfRegistrationInEpoch)
            }, onFailure = {

            })
        }
    }

    private fun epochToDate(value: Long?): LocalDateTime {
        if (value == null) {
            return LocalDateTime.now()
        }
        val epoch = Instant.ofEpochSecond(value)

        return LocalDateTime.ofInstant(epoch, ZoneOffset.UTC)
    }
}