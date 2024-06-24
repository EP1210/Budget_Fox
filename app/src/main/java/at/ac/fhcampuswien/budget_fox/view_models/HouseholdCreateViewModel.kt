package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.UserRepository
import at.ac.fhcampuswien.budget_fox.models.Household
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HouseholdCreateViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private var _householdName = mutableStateOf(value = "").value

    private var _household = MutableStateFlow<Household?>(mutableStateOf(value = null).value)
    val householdName: String
        get() = _householdName

    val household: StateFlow<Household?>
        get() = _household.asStateFlow()

    fun setHouseholdName(householdName: String) {
        _householdName = householdName
    }

    fun createHousehold() : String {
        val household = Household(name = _householdName)
        _household.value = household

        userRepository.insertHousehold(household)
        return household.uuid
    }

}