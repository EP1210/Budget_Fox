package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdCreateViewModel
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import com.lightspark.composeqr.QrCodeView

@Composable
fun HouseholdCreateScreen(
    navigationController: NavController,
    viewModel: HouseholdCreateViewModel,
    userViewModel: UserViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Create a Household"
            ) {
                IconButton(onClick = {
                    navigationController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            if(viewModel.household.collectAsState().value == null) {
                SimpleField(
                    title = "Household name"
                ) { name ->
                    viewModel.setHouseholdName(householdName = name)
                }
                SimpleButton(name = "Create") {
                    val id = viewModel.createHousehold()
                    userViewModel.joinHousehold(id)
                }
            }
            if(viewModel.household.collectAsState().value != null)
            {
                SimpleButton(name = "Go to household") {
                    navigationController.navigate(route = Screen.HouseholdTransaction.route) {
                        popUpTo(id = 0)
                    }
                }
                Text(
                    text = "Join QR-Code for other users",
                    modifier = Modifier.padding(top = 16.dp)
                )
                QrCodeView(
                    data = viewModel.household.collectAsState().value!!.uuid,
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
