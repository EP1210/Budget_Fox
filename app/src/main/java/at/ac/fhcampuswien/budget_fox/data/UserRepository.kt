package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Expense
import at.ac.fhcampuswien.budget_fox.models.Income
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class UserRepository : UserDataAccessObject {

    private val database = Firebase.firestore

    override fun insertUser(user: User, userId: String) {
        database.collection("users").document(userId).set(user.userToDatabase(uid = userId))
    }

    override fun getUser(userId: String): User? {
        var user: User? = null

        database.collection("users").document(userId).get().addOnSuccessListener {
            user =  it.toObject<User>()
        }
        return user
    }

    override fun deleteUser(userId: String) {
        database.collection("users").document(userId).delete()
    }

    override fun insertIncome(income: Income, userId: String) {
        database.collection("users").document(userId).collection("incomes").document(income.uuid.toString()).set(income)
    }

    override fun deleteIncome(income: Income, userId: String) {
        database.collection("users").document(userId).collection("incomes").document(income.uuid.toString()).delete()
    }

    override fun insertExpense(userId: String, expense: Expense) {
        database.collection("users").document(userId).collection("expenses").document(expense.uuid.toString()).set(expense)
    }

    override fun getIncomes(user: User) {
        database.collection("users").document(user.getUid()).collection("incomes").get().addOnSuccessListener {query ->
            query.documents.forEach {document ->
                document.toObject<Income>()?.let { user.addIncome(it) }
            }
        }
    }

    override fun getExpenses(user: User) {
        database.collection("users").document(user.getUid()).collection("expenses").get().addOnSuccessListener {query ->
            query.documents.forEach {document ->
                document.toObject<Expense>()?.let { user.addExpense(it) }
            }
        }
    }


}