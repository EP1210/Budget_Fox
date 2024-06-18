package at.ac.fhcampuswien.budget_fox.view_models

import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Household

class HouseholdViewModel : ViewModel() {
    val userRepository = UserRepository()
    fun getHousehold(householdId: String, onSuccess: (Household?) -> (Unit)) {
        userRepository.getHousehold(householdId, onSuccess)
    }

}