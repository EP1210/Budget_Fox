package at.ac.fhcampuswien.budget_fox.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.budget_fox.data.Repository
import at.ac.fhcampuswien.budget_fox.models.Household
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HouseholdCreateViewModel : ViewModel() {
    private val userRepository = Repository()

    private var _householdName = mutableStateOf(value = "").value

    private var _size = mutableStateOf(value = IntSize.Zero)
    val size: MutableState<IntSize>
        get() = _size

    fun setSize(size: IntSize) {
        _size.value = size
    }

    private var _household = MutableStateFlow<Household?>(mutableStateOf(value = null).value)

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

    fun joinHousehold(userId: String, householdId: String) {
        userRepository.joinHouseholdIfExist(
            userId = userId,
            householdId = householdId
        )
    }
}