package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.R
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.view_models.SavingGoalTransactionListViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleNumberField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import at.ac.fhcampuswien.budget_fox.widgets.TransactionListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavingGoalTransactionListScreen(
    navigationController: NavController,
    userId: String?,
    savingGoalId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: SavingGoalTransactionListViewModel = viewModel(factory = factory)

    if (userId == null || userId == "" || savingGoalId == null || savingGoalId == "") {
        Text("User or saving goal not found")
        return
    }

    viewModel.getTransactions(userId = userId, savingGoalId = savingGoalId)
    viewModel.getDoneState(userId = userId, savingGoalId = savingGoalId)

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Saving goal transactions") {
                IconButton(onClick = {
                    navigationController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            }
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            items(items = viewModel.transactions) { item: Transaction ->
                TransactionListItem(
                    transaction = item,
                    onDelete = {},
                    onItemClick = {})
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
        )
        {
            if(!viewModel.doneState.value)
            {
                FloatingActionButton(
                    onClick = {
                        viewModel.setAlertVisible(visible = true)
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(20.dp)
                        .align(alignment = Alignment.BottomStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check_circle),
                        contentDescription = "Mark saving goal as done")
                }
                FloatingActionButton(
                    onClick = {
                        viewModel.setBottomSheetVisible(visible = true)
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(20.dp)
                        .align(alignment = Alignment.BottomEnd)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.savings),
                        contentDescription = "Save money")
                }
            }
        }
        if(viewModel.bottomSheetVisible.value) {
            ModalBottomSheet(onDismissRequest = {
                viewModel.setBottomSheetVisible(visible = false)
                viewModel.setAmount(amount = 0.0)
            }) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Add money to saving goal",
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    )
                    SimpleNumberField(title = "Amount") { amount ->
                        viewModel.setAmount(amount = amount.toDouble())
                    }
                    SimpleButton(
                        name = "Add to goal"
                    ) {
                        viewModel.transferToSavingGoal(
                            userId = userId,
                            savingGoalId = savingGoalId
                        ) {
                            viewModel.getTransactions(userId = userId, savingGoalId = savingGoalId)
                        }
                    }
                }
            }
        }
        if(viewModel.alertVisible.value)
        {
            AlertDialog(
                title = { Text(text = "Mark as done") },
                text = { Text(text = "Are you sure you want to mark this saving goal as done?") },
                onDismissRequest = { viewModel.setAlertVisible(visible = false) },
                confirmButton = {
                    SimpleButton(
                        name = "Yes"
                    ) {
                        viewModel.markSavingGoalAsDone(userId = userId, savingGoalId = savingGoalId) {
                            viewModel.setAlertVisible(visible = false)
                        }
                    }
                },
                dismissButton = {
                    SimpleButton(
                        name = "No"
                    ) {
                        viewModel.setAlertVisible(visible = false)
                    }
                }
            )
        }
    }
}