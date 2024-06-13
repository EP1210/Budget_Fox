package at.ac.fhcampuswien.budget_fox.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdCreateViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import com.lightspark.composeqr.QrCodeView

@Composable
fun HouseholdCreateScreen(
    navigationController: NavController,
    viewModel: HouseholdCreateViewModel
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
            SimpleField(
                title = "Household name"
            ) { name ->
                viewModel.setHouseholdName(householdName = name)
            }
            SimpleButton(name = "Create") {
                viewModel.createHousehold()
            }
            if(viewModel.household.collectAsState().value != null)
            {
                QrCodeView(
                    data = viewModel.household.collectAsState().value!!.uuid,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}
