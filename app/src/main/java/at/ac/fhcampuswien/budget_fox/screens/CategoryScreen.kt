package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.CategoryItem
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleEventIcon
import at.ac.fhcampuswien.budget_fox.widgets.SimpleField
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun CategoryScreen(
    navigationController: NavController,
    viewModel: UserViewModel,
    transactionId: String?
) {
    viewModel.user?.getTransactions()?.forEach { transaction ->
        if (transaction.uuid == transactionId) {
            viewModel.getCategoriesFromUser()

            Scaffold(
                topBar = {
                    SimpleTopAppBar(
                        title = transaction.description
                    ) {
                        SimpleEventIcon(
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "An arrow icon to navigate back to the previous screen"
                        ) {
                            navigationController.popBackStack()
                        }
                    }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues = it)
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
                        name = "Create category",
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    ) {
                        if (viewModel.categoryName.isNotBlank()) {
                            viewModel.insertCategory(
                                category = Category(
                                    name = viewModel.categoryName,
                                    description = viewModel.categoryDescription
                                )
                            )
                            viewModel.setCategoryName(categoryName = "")
                            viewModel.setCategoryDescription(categoryDescription = "")
                        }
                        viewModel.getCategoriesFromUser()
                    }
                    CategoryList(transactionId = transaction.uuid, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun CategoryList(
    viewModel: UserViewModel,
    transactionId: String
) {
    LazyColumn {
        items(items = viewModel.categoriesFromUser) { category ->
            CategoryItem(
                categoryName = category.name,
                categoryDescription = category.description,
                edit = {
                    SimpleEventIcon(
                        icon = Icons.Default.Edit,
                        colour = Color.Blue,
                        contentDescription = "An icon to edit the category"
                    ) {
                        // todo: logic to edit the category item
                    }
                },
                delete = {
                    SimpleEventIcon(
                        icon = Icons.Default.Delete,
                        colour = Color.Red,
                        contentDescription = "An icon to delete the category"
                    ) {
                        viewModel.deleteCategory(categoryId = category.uuid)
                        viewModel.getCategoriesFromUser()
                    }
                },
                toggle = {
                    Checkbox(
                        checked = transactionId in category.transactionMemberships,
                        onCheckedChange = {
                            if (transactionId in category.transactionMemberships) {
                                category.transactionMemberships.remove(transactionId)
                            } else {
                                category.transactionMemberships.add(transactionId)
                            }
                            viewModel.updateCategoryTransactionMemberships(category = category)
                            viewModel.getCategoriesFromUser()
                        }
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 5.dp)
            )
        }
    }
}