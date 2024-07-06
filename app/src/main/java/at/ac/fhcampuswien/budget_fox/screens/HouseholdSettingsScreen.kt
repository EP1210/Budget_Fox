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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdSettingsViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import com.lightspark.composeqr.QrCodeView

@Composable
fun HouseholdSettingsScreen(
    householdId: String?,
    userId: String?,
    navigationController: NavController
) {

    val factory = ViewModelFactory()
    val viewModel: HouseholdSettingsViewModel = viewModel(factory = factory)

    if (userId == null || userId == "" || householdId == null || householdId == "") {
        Text("User or household not found")
        return
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Household settings") {
                IconButton(onClick = {
                    navigationController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        },
        modifier = Modifier.onSizeChanged { screenSize ->
            viewModel.setSize(screenSize)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            FilledTonalButton(
                onClick = {
                    viewModel.leaveHousehold(userId = userId, onSuccess = {
                        navigationController.navigate(Screen.UserProfile.passUserId(userId = userId))
                    },
                        onFailure = {
                            //TODO Show error
                        })
                },
                modifier = Modifier
                    .padding(all = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Magenta,
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = "Leave household"
                )
            }
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 2.dp,
                modifier = Modifier.padding(all = 16.dp)
            )
            Text(
                text = "Join code for household:",
                modifier = Modifier.padding(horizontal = 16.dp)
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
                    data = householdId,
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .fillMaxSize()
                )
            }
        }
    }
}