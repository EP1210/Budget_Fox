package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class UserRepository : UserDataAccessObject {

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

    override fun insertTransaction(userId: String, transaction: Transaction) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .document(transaction.uuid.toString()).set(transaction)
    }

    override fun deleteTransaction(userId: String, transactionId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).collection(DatabaseCollection.Transactions.collectionName)
            .document(transactionId).delete()
    }

    override fun getTransactionsFromUser(userId: String): List<Transaction> {
        val transactionList = mutableListOf<Transaction>()
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName).get()
            .addOnSuccessListener { transactions ->
                for (transaction in transactions) {
                    transactionList.add(transaction.toObject<Transaction>())
                }
            }
        return transactionList
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
}