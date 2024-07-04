package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Transaction

class CategoryViewModel : ViewModel() {
    private val repository = UserRepository()

    private var _transaction = mutableStateOf<Transaction?>(value = null)
    val transaction: MutableState<Transaction?>
        get() = _transaction

    private var _categoriesFromUser = mutableStateListOf<Category>()
    val categoriesFromUser: List<Category>
        get() = _categoriesFromUser

    private var _categoryName = mutableStateOf(value = "").value
    val categoryName: String
        get() = _categoryName

    private var _categoryDescription = mutableStateOf(value = "").value
    val categoryDescription: String
        get() = _categoryDescription

    fun setCategoryName(categoryName: String) {
        _categoryName = categoryName
    }

    fun setCategoryDescription(categoryDescription: String) {
        _categoryDescription = categoryDescription
    }

    fun getTransaction(userId: String, transactionId: String) {
        repository.getTransaction(userId, transactionId) {
            _transaction.value = it
        }
    }

    fun insertCategory(userId: String, category: Category) {
            repository.insertCategory(userId = userId, category = category)
    }

    fun getCategoriesFromUser(userId: String) {
        repository.getCategoriesFromUser(userId = userId) { categories ->
            _categoriesFromUser.clear()
            _categoriesFromUser.addAll(categories)
        }
    }

    fun updateCategoryTransactionMemberships(userId: String, category: Category) {
        repository.updateCategoryTransactionMemberships(
            userId = userId,
            category = category
        )
    }

    fun deleteCategory(userId: String, categoryId: String) {
        repository.deleteCategory(userId = userId, categoryId = categoryId)
    }
}