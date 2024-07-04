package at.ac.fhcampuswien.budget_fox.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class WelcomeViewModel : ViewModel() {
    private val uid = Firebase.auth.currentUser?.uid

    fun checkIfUserIsLoggedIn(onLoggedIn: (String) -> Unit) {
        if (uid != null && uid != "") {
            onLoggedIn(uid)
        }
        else
        {
            Log.d("FIREBASE", "User is not logged in!")
        }
    }
}