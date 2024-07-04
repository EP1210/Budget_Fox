package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime

class RegistrationViewModel : ViewModel() {
    //region Input fields
    private var _email = mutableStateOf(value = "").value
    private var _dateOfBirth = mutableStateOf(value = LocalDateTime.now())
    private var _password = mutableStateOf(value = "").value
    private var _firstName = mutableStateOf(value = "").value
    private var _lastName = mutableStateOf(value = "").value
    private var _errorMessage = mutableStateOf(value = "")

    val email: String
        get() = _email

    val dateOfBirth: MutableState<LocalDateTime>
        get() = _dateOfBirth

    val password: String
        get() = _password

    val firstName: String
        get() = _firstName

    val lastName: String
        get() = _lastName

    val errorMessage: MutableState<String>
        get() = _errorMessage

    fun setEmail(email: String) {
        _email = email
    }

    fun setPassword(password: String) {
        _password = password
    }

    fun setFirstName(firstName: String) {
        _firstName = firstName
    }

    fun setLastName(lastName: String) {
        _lastName = lastName
    }

    fun setDateOfBirth(dateOfBirth: LocalDateTime) {
        _dateOfBirth.value = dateOfBirth
    }
    //endregion

    fun registerUser(
        onSuccess: (String) -> Unit
    ) {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Please fill out all fields!"
            return
        }

        val user = User(
            firstName = firstName,
            lastName = lastName,
            dateOfBirth = dateOfBirth.value,
            dateTimeOfRegistration = LocalDateTime.now()
        )

        val auth = Firebase.auth
        val database = Firebase.firestore

        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = auth.currentUser
                val uid = firebaseUser?.uid

                if (uid != null) {
                    val newDocRef = database.collection("users").document(uid)
                    val batch = database.batch()

                    batch.set(newDocRef, user.userToDatabase())
                    batch.commit()
                        .addOnSuccessListener {
                            onSuccess(uid)
                        }
                        .addOnFailureListener {
                            _errorMessage.value = "Error with database system!"
                        }
                }
            }
            .addOnFailureListener {
                _errorMessage.value = "Registration failed!"
            }
    }
}