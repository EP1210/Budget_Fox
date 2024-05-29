package at.ac.fhcampuswien.budget_fox.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String, arrowBack: @Composable () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title
            )},
        navigationIcon = arrowBack
    )
}