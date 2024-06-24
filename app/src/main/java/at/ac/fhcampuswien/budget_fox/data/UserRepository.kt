package at.ac.fhcampuswien.budget_fox.data

import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Household
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class UserRepository : UserDataAccessObject, HouseholdDataAccessObject {

    private val database = Firebase.firestore

    override fun insertUser(user: User, userId: String, onSuccess: () -> Unit) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).set(user.userToDatabase())
            .addOnSuccessListener { onSuccess() }
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
            .addOnSuccessListener {
                onSuccess()
            }
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

    override fun deleteTransaction(userId: String, transactionId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).collection(DatabaseCollection.Transactions.collectionName)
            .document(transactionId).delete()
    }

    override fun insertCategoryAtUser(userId: String, category: Category) {
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
            .addOnSuccessListener { categoryDocuments ->
                categoryDocuments.forEach { categoryDocument ->
                    categoriesFromUser.add(categoryDocument.toObject<Category>())
                }
                onSuccess(categoriesFromUser)
            }
    }

    override fun deleteCategoryAtUser(userId: String, categoryId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(categoryId).delete()
    }

    override fun insertCategoryAtTransaction(
        userId: String,
        categoryId: String,
        transactionId: String
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(categoryId).get()
            .addOnSuccessListener { categoryDocument ->
                val category = categoryDocument.toObject<Category>()

                if (category != null) {
                    database
                        .collection(DatabaseCollection.Users.collectionName)
                        .document(userId)
                        .collection(DatabaseCollection.Transactions.collectionName)
                        .document(transactionId)
                        .collection(DatabaseCollection.Categories.collectionName)
                        .document(categoryId).set(category)
                }
            }
    }

    override fun getIdsFromCategoriesAtTransaction(
        userId: String,
        transactionId: String,
        onSuccess: (List<String>) -> Unit
    ) {
        val categoryIds = mutableListOf<String>()

        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .document(transactionId)
            .collection(DatabaseCollection.Categories.collectionName).get()
            .addOnSuccessListener { categoryDocuments ->
                categoryDocuments.forEach { categoryDocument ->
                    categoryIds.add(categoryDocument.id)
                }
                onSuccess(categoryIds)
            }
    }

    override fun deleteCategoryAtTransaction(
        userId: String,
        transactionId: String,
        categoryId: String
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .document(transactionId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(categoryId).delete()
    }

    override fun deleteCategoryAtAllTransactions(userId: String, categoryId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName).get()
            .addOnSuccessListener { transactionDocuments ->
                transactionDocuments.forEach { transactionDocument ->
                    database
                        .collection(DatabaseCollection.Users.collectionName)
                        .document(userId)
                        .collection(DatabaseCollection.Transactions.collectionName)
                        .document(transactionDocument.id)
                        .collection(DatabaseCollection.Categories.collectionName).get()
                        .addOnSuccessListener { categoryDocuments ->
                            categoryDocuments.forEach { categoryDocument ->
                                if (categoryDocument.id == categoryId) {
                                    database
                                        .collection(DatabaseCollection.Users.collectionName)
                                        .document(userId)
                                        .collection(DatabaseCollection.Transactions.collectionName)
                                        .document(transactionDocument.id)
                                        .collection(DatabaseCollection.Categories.collectionName)
                                        .document(categoryDocument.id).delete()
                                }
                            }
                        }
                }
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
            .collection(DatabaseCollection.Households.collectionName)
            .document(household.uuid)
            .set(household.householdToDatabase())
    }

    override fun getHousehold(householdId: String, onSuccess: (Household?) -> Unit) {
        database
            .collection(DatabaseCollection.Households.collectionName)
            .document(householdId)
            .get()
            .addOnSuccessListener { household ->
                onSuccess(household.toObject<Household>())
            }
    }

    override fun deleteHousehold(householdId: String) {
        database
            .collection(DatabaseCollection.Households.collectionName)
            .document(householdId)
            .delete()
    }

    override fun insertHouseholdTransaction(transaction: Transaction, householdId: String) {
        database
            .collection(DatabaseCollection.Households.collectionName)
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
            .collection(DatabaseCollection.Households.collectionName)
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

    override fun joinHouseholdIfExist(
        userId: String,
        householdId: String,
        onSuccess: () -> Unit,
        notExits: () -> Unit
    ) {
        val householdRef = database
            .collection(DatabaseCollection.Households.collectionName)
            .document(householdId)

        householdRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                getAllDataFromUser(userId,
                    onSuccess = { user ->
                        if (user != null) {
                            user.joinHousehold(householdId)
                            insertUser(user, userId, onSuccess = {
                                onSuccess()
                            })
                        }
                    },
                    onFailure = {})
            }
            else
            {
                notExits()
            }
        }
            .addOnFailureListener {
                notExits()
            }
    }
}