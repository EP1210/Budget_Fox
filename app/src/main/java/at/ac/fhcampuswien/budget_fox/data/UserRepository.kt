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
            user = it.toObject<User>()
        }
        return user
    }

    override fun deleteUser(userId: String) {
        database.collection("users").document(userId).delete()
    }

    override fun insertIncome(income: Income, userId: String) {
        database.collection("users").document(userId).collection("incomes")
            .document(income.uuid.toString()).set(income)
    }

    override fun deleteIncome(income: Income, userId: String) {
        database.collection("users").document(userId).collection("incomes")
            .document(income.uuid.toString()).delete()
    }

    override fun insertExpense(userId: String, expense: Expense) {
        database.collection("users").document(userId).collection("expenses")
            .document(expense.uuid.toString()).set(expense)
    }

    override fun getExpensesFromUser(userId: String): List<Expense> {
        val expenses = mutableListOf<Expense>()
        database.collection("users").document(userId).collection("expenses").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    expenses.add(document.toObject<Expense>())
                }
            }
        return expenses
    }

    override fun getIncomesFromUser(userId: String): List<Income> {
        val incomes = mutableListOf<Income>()
        database.collection("users").document(userId).collection("incomes").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    incomes.add(document.toObject<Income>())
                }
            }
        return incomes
    }

    // TODO: Single responsibility principle!
    override fun getAllDataFromUser(
        userId: String,
        onSuccess: (User?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        var user: User? = null

        database.collection("users").document(userId).get()
            .addOnSuccessListener {
                user = it.toObject<User>()
                database.collection("users").document(userId).collection("expenses").get()
                    .addOnSuccessListener { expenseDocuments ->
                        for (document in expenseDocuments) {
                            user?.addExpense(document.toObject<Expense>())
                        }
                        database.collection("users").document(userId).collection("incomes").get()
                            .addOnSuccessListener { incomeDocuments ->
                                for (document in incomeDocuments) {
                                    user?.addIncome(document.toObject<Income>())
                                }
                                onSuccess(user)
                            }
                            .addOnFailureListener(onFailure)
                    }
                    .addOnFailureListener(onFailure)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }
}