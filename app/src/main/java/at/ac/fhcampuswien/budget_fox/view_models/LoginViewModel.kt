package at.ac.fhcampuswien.budget_fox.view_models

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {
    private var repository = UserRepository()

    //region Input fields
    private var _email = mutableStateOf(value = "").value
    private var _password = mutableStateOf(value = "").value
    private var _errorMessage = mutableStateOf(value = "")

    val email : String
        get() = _email

    val password : String
        get() = _password

    val errorMessage : MutableState<String>
        get() = _errorMessage


    fun setEmail(email: String) {
        _email = email
    }

    fun setPassword(password: String) {
        _password = password
    }
    //endregion

    fun userLogin(
        onSuccess: (String) -> Unit
    ) {
        if (email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Please fill out all fields!"
            return
        }

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = Firebase.auth.currentUser
                    val uid = firebaseUser?.uid

                    if (uid != null) {
                        repository.getAllDataFromUser(uid,
                            onSuccess = { user ->
                                if (user != null) {
                                    onSuccess(uid)
                                } else {
                                    Log.d("FIREBASE", "USER IS NULL!")
                                }
                            }, onFailure = {
                                _errorMessage.value = "User could not be loaded!"
                            }
                        )
                    } else {
                        _errorMessage.value = "User ID is not correct!"
                    }
                } else {
                    _errorMessage.value = "The credentials are not correct!"
                }
            }
    }
}