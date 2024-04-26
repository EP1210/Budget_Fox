package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Income
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class UserRepository() : UserDataAccessObject {

    private val database = Firebase.firestore

    override fun addUser(user: User, uid: String) {
        database.collection("users").document(uid).set(user.userToDatabase(uid = uid))
    }

    override fun getUser(uid: String): User? {
        var user: User? = null

        database.collection("users").document(uid).get().addOnSuccessListener {
            user =  it.toObject<User>()
        }
        return user
    }

    override fun deleteUser(uid: String) {
        database.collection("users").document(uid).delete()
    }

    override fun addIncome(income: Income, uid: String) {
        database.collection("users").document(uid).collection("incomes").document(income.uid.toString()).set(income)
    }

    override fun deleteIncome(income: Income, uid: String, ) {
        database.collection("users").document(uid).collection("incomes").document(income.uid.toString()).delete()
    }
}