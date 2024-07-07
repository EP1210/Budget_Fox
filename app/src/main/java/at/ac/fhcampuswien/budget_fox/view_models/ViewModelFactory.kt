package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) { // https://stackoverflow.com/questions/69150992/how-can-i-create-common-viewmodelfactory-with-repository-in-kotlin-android
            RegistrationViewModel::class.java -> RegistrationViewModel()
            LoginViewModel::class.java -> LoginViewModel()
            UserProfileViewModel::class.java -> UserProfileViewModel()
            SavingGoalOverviewViewModel::class.java -> SavingGoalOverviewViewModel()
            SavingGoalAddViewModel::class.java -> SavingGoalAddViewModel()
            TransactionListViewModel::class.java -> TransactionListViewModel()
            TransactionViewModel::class.java -> TransactionViewModel()
            RegularTransactionViewModel::class.java -> RegularTransactionViewModel()
            CategoryViewModel::class.java -> CategoryViewModel()
            StatisticsViewModel::class.java -> StatisticsViewModel()
            WelcomeViewModel::class.java -> WelcomeViewModel()
            HouseholdWelcomeViewModel::class.java -> HouseholdWelcomeViewModel()
            HouseholdSettingsViewModel::class.java -> HouseholdSettingsViewModel()
            HouseholdTransactionViewModel::class.java -> HouseholdTransactionViewModel()
            HouseholdTransactionAddViewModel::class.java -> HouseholdTransactionAddViewModel()
            HouseholdJoinViewModel::class.java -> HouseholdJoinViewModel()
            HouseholdCreateViewModel::class.java -> HouseholdCreateViewModel()
            SavingGoalTransactionListViewModel::class.java -> SavingGoalTransactionListViewModel()
            BudgetViewModel::class.java -> BudgetViewModel()
            else -> throw IllegalArgumentException("Unknown ViewModel class!")
        } as T
}