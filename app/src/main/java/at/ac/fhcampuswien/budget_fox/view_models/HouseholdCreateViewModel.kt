package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Household

class HouseholdCreateViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private var _householdName = mutableStateOf(value = "").value
    val householdName: String
        get() = _householdName

    fun setHouseholdName(householdName: String) {
        _householdName = householdName
    }

    fun insertHousehold() {
        val household = Household(name = _householdName)

        userRepository.insertHousehold(household = household)
    }

}