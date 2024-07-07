package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.models.Category
import at.ac.fhcampuswien.budget_fox.models.Transaction
import at.ac.fhcampuswien.budget_fox.view_models.CategoryViewModel
import at.ac.fhcampuswien.budget_fox.view_models.TransactionListViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleEventIcon
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

/*
 * Source: https://medium.com/@developerchunk/create-custom-pie-chart-with-animations-in-jetpack-compose-android-studio-kotlin-49cf95ef321e
 */

val Purple200 = Color(0xFFBB86FC)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val Blue = Color(0xFF2196F3)
val Yellow = Color(0xFFFFEB3B)
val Red = Color(0xFFF44336)
val Green = Color(0xFF4CAF50)
val Grey = Color(0xFF9E9E9E)
val Orange = Color(0xFFFF9800)
val LightBlue = Color(0xFF81D4FA)
val LightGreen = Color(0xFF8BC34A)
val Pink = Color(0xFFE91E63)

@Composable
fun CategoriesStatisticsScreen(
    navigationController: NavController,
    userId: String?
) {
    if (userId.isNullOrEmpty()) {
        Text("User not found")
        return
    }

    val factory = ViewModelFactory()
    val categoryViewModel: CategoryViewModel = viewModel(factory = factory)
    val transactionListViewModel: TransactionListViewModel = viewModel(factory = factory)

    LaunchedEffect(Unit) {
        categoryViewModel.getCategoriesFromUser(userId)
        transactionListViewModel.loadTransactions(userId)
    }

    val categories by remember { derivedStateOf { categoryViewModel.categoriesFromUser } }
    val transactions by remember { derivedStateOf { transactionListViewModel.transactions } }
    var showIncomes by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Statistics by Categories") {
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
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            Button(
                onClick = { showIncomes = !showIncomes },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 16.dp)
            ) {
                Text(text = if (showIncomes) "Switch to Expense" else "Switch to Income")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (showIncomes) "Income Statistics" else "Expense Statistics",
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 40.dp)
            )

            if (showIncomes) {
                PieChart(
                    data = getPieChartData(transactions, categories, true)
                )
            } else {
                PieChart(
                    data = getPieChartData(transactions, categories, false)
                )
            }
        }
    }
}

@Composable
fun PieChart(
    data: Map<String, Int>,
    radiusOuter: Dp = 115.dp,
    chartBarWidth: Dp = 35.dp,
    animDuration: Int = 1200,
) {

    val totalSum = data.values.sum()
    val floatValue = mutableListOf<Float>()

    data.values.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = listOf(
        Purple200,
        Teal200,
        Purple700,
        Blue,
        Yellow,
        Red,
        Green,
        Grey,
        Orange,
        LightBlue,
        LightGreen,
        Pink
    )

    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        startAngle = lastValue,
                        sweepAngle = value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        DetailsPieChart(
            data = data,
            colors = colors
        )
    }
}

@Composable
fun DetailsPieChart(
    data: Map<String, Int>,
    colors: List<Color>
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxWidth()
    ) {
        items(data.entries.toList()) { entry ->
            val index = data.entries.indexOf(entry)
            DetailsPieChartItem(
                data = Pair(entry.key, entry.value),
                color = colors[index % colors.size]
            )
        }
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 45.dp,
    color: Color
) {
    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 40.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 22.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

fun getPieChartData(transactions: List<Transaction>, categories: List<Category>, isIncome: Boolean): Map<String, Int> {
    val filteredTransactions = transactions.filter { transaction ->
        if (isIncome) transaction.amount > 0 else transaction.amount < 0
    }

    return categories.associate { category ->
        val totalAmount = category.transactionMemberships.sumOf { transactionId ->
            filteredTransactions.find { it.uuid == transactionId }?.amount?.toInt() ?: 0
        }
        category.name to totalAmount
    }.filterValues { it != 0 }
}