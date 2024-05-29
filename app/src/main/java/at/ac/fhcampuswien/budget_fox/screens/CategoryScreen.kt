package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun CategoryScreen(
    navigationController: NavController,
    viewModel: UserViewModel
) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Manage your categories"
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
                title = "Category name"
            ) { name ->
                viewModel.setCategoryName(categoryName = name)
            }
            SimpleField(
                title = "Description"
            ) { description ->
                viewModel.setCategoryDescription(categoryDescription = description)
            }

            SimpleButton(
                name = "Add category"
            ) {
                viewModel.insertCategory(category = Category(
                    name = viewModel.categoryName,
                    description = viewModel.categoryDescription
                ))
            }
        }
    }
}