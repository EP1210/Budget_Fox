package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    if (transactionId != null) {
        val transaction = viewModel.user?.getTransactions()?.find { transaction ->
            transaction.uuid == transactionId
        }

        viewModel.getCategoriesFromUser()

        Scaffold(
            topBar = {
                SimpleTopAppBar(
                    title = "Manage \"${transaction?.description}\""
                ) {
                    SimpleEventIcon(
                        icon = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "An icon to navigate back to the previous screen"
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

                LazyColumn {
                    items(items = viewModel.categoriesFromUser) { category ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            CategoryItem(
                                categoryName = category.name,
                                categoryDescription = category.description,
                                edit = {
                                    // todo: logic to edit the category item
                                },
                                delete = {
                                    viewModel.deleteCategory(categoryId = category.uuid)
                                    viewModel.getCategoriesFromUser()
                                }
                            )
                            Checkbox(
                                checked = false,
                                onCheckedChange = {
                                    viewModel.insertCategoryAtTransaction(
                                        categoryId = category.uuid,
                                        transactionId = transactionId
                                    )
                                    viewModel.deleteCategoryAtTransaction(
                                        transactionId = transactionId,
                                        categoryId = category.uuid
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}