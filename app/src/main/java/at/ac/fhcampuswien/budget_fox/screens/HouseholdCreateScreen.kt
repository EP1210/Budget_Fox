package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdCreateViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import com.lightspark.composeqr.QrCodeView

@Composable
fun HouseholdCreateScreen(
    navigationController: NavController,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: HouseholdCreateViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Create a Household"
            ) {
                if (viewModel.household.collectAsState().value == null) {
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
        },
        modifier = Modifier.onSizeChanged {
            viewModel.setSize(size = it)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            if (viewModel.household.collectAsState().value == null) {
                SimpleField(
                    title = "Household name"
                ) { name ->
                    viewModel.setHouseholdName(householdName = name)
                }
                SimpleButton(name = "Create") {
                    val id = viewModel.createHousehold()
                    viewModel.joinHousehold(userId = userId, householdId = id)
                }
            }
            if (viewModel.household.collectAsState().value != null) {
                SimpleButton(name = "Go to household") {
                    navigationController.navigate(route = Screen.HouseholdTransaction.passHouseholdId(householdId = viewModel.householdId, userId = userId)) {
                        popUpTo(id = 0)
                    }
                }
                Text(
                    text = "Join QR-Code for other users",
                    modifier = Modifier.padding(top = 16.dp)
                )
                Box(
                    modifier = Modifier
                        .then(
                            with(LocalDensity.current) {
                                Modifier.size(
                                    width = viewModel.size.value.width.toDp(),
                                    height = viewModel.size.value.width.toDp(),
                                )
                            }
                        )
                        .background(color = Color.White),
                ) {
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
}
