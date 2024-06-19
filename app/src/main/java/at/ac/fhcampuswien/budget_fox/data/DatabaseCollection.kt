package at.ac.fhcampuswien.budget_fox.data

sealed class DatabaseCollection (val collectionName: String){
    data object Users : DatabaseCollection(collectionName = "users")
    data object Transactions : DatabaseCollection(collectionName = "transactions")
    data object Categories : DatabaseCollection(collectionName = "categories")
    data object Households: DatabaseCollection(collectionName = "households")
}