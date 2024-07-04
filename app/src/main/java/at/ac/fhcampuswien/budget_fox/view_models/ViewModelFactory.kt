package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserProfileViewModel::class.java))
            return UserProfileViewModel() as T
        if(modelClass.isAssignableFrom(SavingGoalOverviewViewModel::class.java))
            return SavingGoalOverviewViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}