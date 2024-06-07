package at.ac.fhcampuswien.budget_fox.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.view_models.StatisticsViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleBottomNavigationBar
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import java.text.DateFormatSymbols
import java.util.Locale

@Composable
fun StatisticsScreen(
    navigationController: NavController,
    route: String,
    viewModel: StatisticsViewModel
) {
    viewModel.mapTransactionsFromUserToMonths(year = 2024)
    // Help: https://github.com/patrykandpatrick/vico/blob/master/sample/src/main/java/com/patrykandpatrick/vico/sample/showcase/charts/Chart3.kt
    val modelProducer = remember { CartesianChartModelProducer.build() }
    val incomes by viewModel.incomes.collectAsState()
    val expenses by viewModel.expenses.collectAsState()

    modelProducer.tryRunTransaction {
        /* Learn more:
        https://patrykandpatrick.com/vico/wiki/cartesian-charts/layers/line-layer#data. */
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

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Statistics"
            )
        },
        bottomBar = {
            SimpleBottomNavigationBar(
                navigationController = navigationController,
                currentRoute = route
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
            ComposeChart1(
                modelProducer = modelProducer,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun ComposeChart1(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
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
        modelProducer = modelProducer,
        modifier = modifier,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

private const val COLUMN_THICKNESS_DP: Int = 10
private val colorGreen = Color(0xFF008000)
private val colorRed = Color(0xFFFF0000)

private val monthNames = DateFormatSymbols.getInstance(Locale.US).shortMonths
private val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->
    monthNames[x.toInt()]
}