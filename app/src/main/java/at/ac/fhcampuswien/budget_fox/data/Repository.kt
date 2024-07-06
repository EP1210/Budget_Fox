package at.ac.fhcampuswien.budget_fox.data

import android.util.Log
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Household
import at.ac.fhcampuswien.budget_fox.models.SavingGoal
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.models.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

class Repository : UserDataAccessObject, HouseholdDataAccessObject {

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
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .document(transaction.uuid).set(transaction.transactionToDatabase())
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
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
                    val data = transaction.data
                    val parsedTransaction = Transaction.fromDatabase(data)
                    transactionList.add(parsedTransaction)
                }
                onSuccess(transactionList)
            }
            .addOnFailureListener(onFailure)
    }

    override fun getSpecificTransaction(
        userId: String,
        transactionId: String,
        onSuccess: (Transaction) -> Unit
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Transactions.collectionName)
            .document(transactionId)
            .get()
            .addOnSuccessListener { transaction ->
                transaction.data?.let {
                    onSuccess(Transaction.fromDatabase(it))
                }
            }
    }

    override fun deleteTransaction(userId: String, transactionId: String, onComplete: () -> Unit) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId).collection(DatabaseCollection.Transactions.collectionName)
            .document(transactionId).delete()
            .addOnSuccessListener {
                onComplete()
            }
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
            .addOnSuccessListener { categoryDocuments ->
                categoryDocuments.forEach { categoryDocument ->
                    categoriesFromUser.add(categoryDocument.toObject<Category>())
                }
                onSuccess(categoriesFromUser)
            }
    }

    override fun updateCategoryName(userId: String, categoryId: String, newCategoryName: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(categoryId).update("name", newCategoryName)
    }

    override fun updateCategoryDescription(
        userId: String,
        categoryId: String,
        newCategoryDescription: String
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(categoryId).update("description", newCategoryDescription)
    }

    override fun updateCategoryTransactionMemberships(userId: String, category: Category) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(category.uuid).set(category)
    }

    override fun deleteCategory(userId: String, categoryId: String) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.Categories.collectionName)
            .document(categoryId).delete()
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
                            val data = transaction.data
                            val parsedTransaction = Transaction.fromDatabase(data)
                            user?.addTransaction(parsedTransaction)
                        }
                        onSuccess(user)
                    }
                    .addOnFailureListener(onFailure)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    override fun getSavingGoalsFromUser(userId: String, onSuccess: (List<SavingGoal>) -> Unit) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.SavingGoals.collectionName)
            .get()
            .addOnSuccessListener { savingGoals ->
                val goals = mutableListOf<SavingGoal>()
                savingGoals.forEach { goal ->
                    goals.add(goal.toObject<SavingGoal>())
                }
                getSavingGoalsTransactions(userId = userId, goals = goals, onSuccess = onSuccess)
            }
    }

    override fun getSavingGoalsTransactions(
        userId: String,
        goals: List<SavingGoal>,
        onSuccess: (List<SavingGoal>) -> Unit
    ) {
        for (i in goals.indices) {
            database
                .collection(DatabaseCollection.Users.collectionName)
                .document(userId)
                .collection(DatabaseCollection.SavingGoals.collectionName)
                .document(goals[i].uuid)
                .collection(DatabaseCollection.Transactions.collectionName)
                .get()
                .addOnSuccessListener { transactions ->
                    transactions.forEach { transaction ->
                        goals[i].addTransaction(transaction.toObject<Transaction>())
                    }
                    if (i == goals.size - 1)
                        onSuccess(goals)
                }
        }
    }

    override fun savingGoalToDatabase(
        userId: String,
        savingGoal: SavingGoal,
        onSuccess: () -> Unit
    ) {
        val transactions = savingGoal.getTransactions()
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.SavingGoals.collectionName)
            .document(savingGoal.uuid)
            .set(savingGoal.savingGoalToDatabase())
            .addOnSuccessListener {
                for (i in transactions.indices) {
                    database
                        .collection(DatabaseCollection.Users.collectionName)
                        .document(userId)
                        .collection(DatabaseCollection.SavingGoals.collectionName)
                        .document(savingGoal.uuid)
                        .collection(DatabaseCollection.Transactions.collectionName)
                        .document(transactions[i].uuid)
                        .set(transactions[i].transactionToDatabase())
                        .addOnSuccessListener {
                            if (i == transactions.size - 1) {
                                onSuccess()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d("FIREBASE", exception.toString())
                        }
                }
                if (transactions.isEmpty())
                    onSuccess()
            }
            .addOnFailureListener { exception ->
                Log.d("FIREBASE", exception.toString())
            }
    }

    override fun getTransactionsForSpecificSavingGoal(
        userId: String,
        savingGoalId: String,
        onSuccess: (List<Transaction>) -> Unit
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.SavingGoals.collectionName)
            .document(savingGoalId)
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

    override fun transferToSavingGoal(
        userId: String,
        savingGoalId: String,
        amount: Double,
        onSuccess: () -> Unit
    ) {
        database.collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.SavingGoals.collectionName)
            .document(savingGoalId)
            .get()
            .addOnSuccessListener { goal ->
                val savingGoalName = goal.toObject<SavingGoal>()?.name

                val transaction = Transaction(
                    amount = amount * -1,
                    description = "Transfer to saving goal: \"$savingGoalName\"",
                    date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                )

                insertTransaction(userId, transaction, onSuccess = {
                    transaction.amount *= -1
                    transaction.description = "Transfer"
                    database
                        .collection(DatabaseCollection.Users.collectionName)
                        .document(userId)
                        .collection(DatabaseCollection.SavingGoals.collectionName)
                        .document(savingGoalId)
                        .collection(DatabaseCollection.Transactions.collectionName)
                        .document(transaction.uuid)
                        .set(transaction.transactionToDatabase())
                        .addOnSuccessListener {
                            onSuccess()
                        }
                }, onFailure = {})
            }
    }

    override fun markSavingGoalAsDone(userId: String, savingGoalId: String, onSuccess: () -> Unit) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.SavingGoals.collectionName)
            .document(savingGoalId)
            .get()
            .addOnSuccessListener { goal ->
                val savingGoal = goal.toObject<SavingGoal>()
                getTransactionsForSpecificSavingGoal(userId = userId, savingGoalId = savingGoalId) {
                    transactions ->
                    transactions.forEach { transaction ->
                        if (savingGoal != null) {
                            savingGoal.addTransaction(transaction)
                        val amount = savingGoal.getProgress()

                            val chargebackTransaction = Transaction(
                                amount = amount,
                                description = "Saving goal \"${savingGoal.name}\" done",
                                date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                            )
                            insertTransaction(userId = userId, transaction = chargebackTransaction, onSuccess = {
                                savingGoal.isDone = true
                                savingGoalToDatabase(userId = userId, savingGoal = savingGoal, onSuccess = {
                                    onSuccess()
                                })
                            }, onFailure = {})
                        }
                    }

                }
            }
    }

    override fun savingGoalIsDone(
        userId: String,
        savingGoalId: String,
        onSuccess: (Boolean) -> Unit
    ) {
        database
            .collection(DatabaseCollection.Users.collectionName)
            .document(userId)
            .collection(DatabaseCollection.SavingGoals.collectionName)
            .document(savingGoalId)
            .get()
            .addOnSuccessListener { goal ->
                val savingGoal = goal.toObject<SavingGoal>()

                if (savingGoal != null) {
                    onSuccess(savingGoal.isDone)
                }
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
                    val data = transaction.data
                    val parsedTransaction = Transaction.fromDatabase(data)
                    transactionList.add(parsedTransaction)
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
            } else {
                notExits()
            }
        }
            .addOnFailureListener {
                notExits()
            }
    }
}