package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Household
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class UserRepository : UserDataAccessObject, HouseholdDataAccessObject {

    private val database = Firebase.firestore

    override fun insertUser(user: User, userId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).set(user.userToDatabase())
    }

    override fun getUser(userId: String): User? {
        var user: User? = null

        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).get()
            .addOnSuccessListener {
                user = it.toObject<User>()
            }
        return user
    }

    override fun deleteUser(userId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).delete()
    }

    override fun insertTransaction(
        userId: String,
        transaction: Transaction,
        onSuccess: () -> Unit
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .document(transaction.uuid).set(transaction.transactionToDatabase())
            .addOnSuccessListener { onSuccess() }
    }

    override fun deleteTransaction(userId: String, transactionId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).collection(DatabaseCollection.Transactions.collectionName)
            .document(transactionId).delete()
    }

    override fun getTransactionsFromUser(
        userId: String,
        onSuccess: (List<Transaction>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val transactionList = mutableListOf<Transaction>()

        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName).get()
            .addOnSuccessListener { transactions ->
                for (transaction in transactions) {
                    transactionList.add(transaction.toObject<Transaction>())
                }
                onSuccess(transactionList)
            }
            .addOnFailureListener(onFailure)
    }

    override fun insertCategory(userId: String, category: Category) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(category.uuid).set(category)
    }

    override fun getCategoriesFromUser(userId: String, onSuccess: (List<Category>) -> Unit) {
        val categoriesFromUser = mutableListOf<Category>()

        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName).get()
            .addOnSuccessListener { categories ->
                categories.forEach { category ->
                    categoriesFromUser.add(category.toObject<Category>())
                }
                onSuccess(categoriesFromUser)
            }
    }

    // TODO: Single responsibility principle!
    override fun getAllDataFromUser(
        userId: String,
        onSuccess: (User?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        var user: User?

        database.collection(DatabaseCollection.Users.collectionName)
            .document(userId).get()
            .addOnSuccessListener {
                user = it.toObject<User>()
                database.collection(DatabaseCollection.Users.collectionName)
                    .document(userId)
                    .collection(DatabaseCollection.Transactions.collectionName).get()
                    .addOnSuccessListener { transactions ->
                        for (transaction in transactions) {
                            user?.addTransaction(transaction.toObject<Transaction>())
                        }
                        onSuccess(user)
                    }
                    .addOnFailureListener(onFailure)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    override fun insertHousehold(household: Household) {
        database
            .collection(DatabaseCollection.Household.collectionName)
            .document(household.uuid)
            .set(household.householdToDatabase())
    }

    override fun getHousehold(householdId: String, onSuccess: (Household?) -> Unit) {
        database
            .collection(DatabaseCollection.Household.collectionName)
            .document(householdId)
            .get()
            .addOnSuccessListener { household ->
                onSuccess(household.toObject<Household>())
            }
    }

    override fun deleteHousehold(householdId: String) {
        database
            .collection(DatabaseCollection.Household.collectionName)
            .document(householdId)
            .delete()
    }

    override fun insertHouseholdTransaction(transaction: Transaction, householdId: String) {
        database
            .collection(DatabaseCollection.Household.collectionName)
            .document(householdId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .document(transaction.uuid)
            .set(transaction.transactionToDatabase())
    }

    override fun getTransactionsFromHousehold(
        householdId: String,
        onSuccess: (List<Transaction>) -> Unit
    ) {
        database
            .collection(DatabaseCollection.Household.collectionName)
            .document(householdId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .get()
            .addOnSuccessListener { transactions ->
                val transactionList = mutableListOf<Transaction>()

                transactions.forEach { transaction ->
                    transactionList.add(transaction.toObject<Transaction>())
                }
                onSuccess(transactionList)
            }
    }
}