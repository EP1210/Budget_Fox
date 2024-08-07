package at.ac.fhcampuswien.budget_fox.screens

import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.helper.QrCodeAnalyzer
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.HouseholdJoinViewModel
import at.ac.fhcampuswien.budget_fox.view_models.ViewModelFactory
import at.ac.fhcampuswien.budget_fox.widgets.SimpleEventIcon
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTopAppBar

@Composable
fun HouseholdJoinScreen(
    navigationController: NavController,
    userId: String?
) {
    val factory = ViewModelFactory()
    val viewModel: HouseholdJoinViewModel = viewModel(factory = factory)

    if (userId == null || userId == "") {
        Text("User not found")
        return
    }

    val context = LocalContext.current

    //TODO: To view model?
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = true) {
        launcher.launch(android.Manifest.permission.CAMERA)
        //TODO: Better request (https://youtu.be/asl1mFtkMkc?si=k250WM5W2ghBG0R9&t=1220)
    }

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                title = "Join a Household"
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
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (hasCamPermission) {
                AndroidView(
                    factory = { context ->
                        val previewView = PreviewView(context)
                        val preview = Preview.Builder().build()
                        val selector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()
                        preview.setSurfaceProvider(previewView.surfaceProvider)
                        val imageAnalysis = ImageAnalysis.Builder()
                            .setTargetResolution(
                                Size(
                                    previewView.width,
                                    previewView.height
                                )
                            )
                            .setBackpressureStrategy(
                                ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                            )
                            .build()
                        imageAnalysis.setAnalyzer(
                            ContextCompat.getMainExecutor(context),
                            QrCodeAnalyzer { qrScanResult ->
                                if (qrScanResult != "") {
                                    Log.d("QrCodeAnalyzer", "Scanned QRCode: $qrScanResult")
                                    viewModel.joinHousehold(
                                        householdId = qrScanResult,
                                        userId = userId,
                                        onSuccess = { householdId ->
                                            navigationController.navigate(
                                                route = Screen.HouseholdTransaction.passHouseholdId(
                                                    householdId = householdId,
                                                    userId = userId
                                                )
                                            ) {
                                                popUpTo(id = 0)
                                            }
                                        }
                                    )
                                }
                            }
                        )
                        try {
                            cameraProviderFuture.get().bindToLifecycle( //only use when view is used
                                lifecycleOwner,
                                selector,
                                preview,
                                imageAnalysis
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        previewView
                    },
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = viewModel.userMessage.value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }
        }
    }
}