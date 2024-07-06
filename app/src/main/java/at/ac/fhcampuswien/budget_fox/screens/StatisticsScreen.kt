package at.ac.fhcampuswien.budget_fox.screens

import android.graphics.Typeface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.view_models.StatisticsViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.rememberHorizontalLegend
import com.patrykandpatrick.vico.compose.common.rememberLegendItem
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasureContext
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.text.DateFormatSymbols
import java.util.Calendar
import java.util.Locale

/*
 * Source: https://github.com/patrykandpatrick/vico/blob/master/sample/src/main/java/com/patrykandpatrick/vico/sample/showcase/charts/Chart3.kt
 */

@Composable
fun StatisticsScreen(
    navigationController: NavController,
    route: String,
    userId: String?
) {
    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    val factory = ViewModelFactory()
    val viewModel: StatisticsViewModel = viewModel(factory = factory)

    // todo
    val modelProducer = remember { CartesianChartModelProducer.build() }
    val incomes by viewModel.incomes.collectAsState()
    val expenses by viewModel.expenses.collectAsState()
    val selectedYear by viewModel.selectedYear.collectAsState()

    LaunchedEffect(selectedYear) { // coroutine function, when year changes then reload
        viewModel.mapTransactionsFromUserToMonths(selectedYear, userId) {
            modelProducer.tryRunTransaction {
                columnSeries {
                    series(
                        y = incomes.values,
                        x = incomes.keys
                    )
                    series(
                        y = expenses.values,
                        x = expenses.keys
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Statistics of the year $selectedYear"
            )
        },
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route,
                userId = userId
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            YearDropdownMenu(
                selectedYear = selectedYear,
                onYearSelected = { year -> viewModel.setSelectedYear(year, userId) }
            )

            IncomeExpensesChart(
                modelProducer = modelProducer,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun YearDropdownMenu(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val years = (2000..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()

    Box() {
        TextButton(onClick = { expanded = true }) {
            Text("Year: $selectedYear ▼")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            years.forEach { year ->
                DropdownMenuItem(
                    text = { Text(year.toString()) },
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun IncomeExpensesChart(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
    val label = rememberTextComponent()
    val indicator = rememberShapeComponent(Shape.Pill, strokeWidth = 8.dp)

    val marker = rememberDefaultCartesianMarker(
        label = label,
        labelPosition = DefaultCartesianMarker.LabelPosition.Top,
        indicator = indicator,
    )

    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider =
                ColumnCartesianLayer.ColumnProvider.series(
                    rememberLineComponent(
                        color = colorGreen,
                        thickness = COLUMN_THICKNESS_DP.dp
                    ),
                    rememberLineComponent(
                        color = colorRed,
                        thickness = COLUMN_THICKNESS_DP.dp
                    ),
                )
            ),
            legend = rememberLegend(),
            startAxis = rememberStartAxis(),
            bottomAxis =
            rememberBottomAxis(
                valueFormatter = bottomAxisValueFormatter,
                itemPlacer =
                remember {
                    AxisItemPlacer.Horizontal.default(spacing = 1, addExtremeLabelPadding = true)
                },
            ),
        ),
        marker = marker,
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

/*
 * Source: https://github.com/patrykandpatrick/vico/blob/master/sample/src/main/java/com/patrykandpatrick/vico/sample/showcase/charts/Chart7.kt#L109
 */
@Composable
private fun rememberLegend() =
    rememberHorizontalLegend<CartesianMeasureContext, CartesianDrawContext>(
        items =
        chartColors.mapIndexed { index, chartColor ->
            rememberLegendItem(
                icon = rememberShapeComponent(Shape.Pill, chartColor),
                label =
                rememberTextComponent(
                    color = vicoTheme.textColor,
                    textSize = 12.sp,
                    typeface = Typeface.MONOSPACE,
                ),
                labelText = (when (chartColor) {
                    colorGreen -> "Incomes"
                    colorRed -> "Expenses"
                    else -> "Error"
                })
            )
        },
        iconSize = 8.dp,
        iconPadding = 8.dp,
        spacing = 4.dp,
        padding = Dimensions.of(top = 8.dp),
    )

private const val COLUMN_THICKNESS_DP: Int = 10
private val colorGreen = Color(0xFF008000)
private val colorRed = Color(0xFFFF0000)

private val chartColors = listOf(colorGreen, colorRed)

private val monthNames = DateFormatSymbols.getInstance(Locale.US).shortMonths
private val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->
    monthNames[x.toInt()]
}