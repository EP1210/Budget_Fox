package at.ac.fhcampuswien.budget_fox.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.budget_fox.navigation.Screen
import at.ac.fhcampuswien.budget_fox.view_models.UserViewModel
import at.ac.fhcampuswien.budget_fox.widgets.SimpleButton
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTextLink
import at.ac.fhcampuswien.budget_fox.widgets.SimpleTitle
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun UserProfileScreen(
    navController: NavController,
    viewModel: UserViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val auth = Firebase.auth
        val userMail = auth.currentUser?.email
        val userName = auth.currentUser?.displayName

        SimpleTitle(
            title = when (viewModel.newUser) {
                true -> "Registration successful!"
                false -> "Welcome back!"
            }
        )
        Text(
            text = """E-Mail: $userMail
                |Name: $userName
                |Registered since:
            """.trimMargin()
        )

        SimpleButton(name = "Logout") {
            auth.signOut()
            navController.navigate(Screen.Login.route) {
                popUpTo(id = 0)
            }
        }

        // Source: https://medium.com/@daniel.atitienei/picking-images-from-gallery-using-jetpack-compose-a18c11d93e12
        var imageUri by remember {
            mutableStateOf<Uri?>(null)
        }

        val galleryLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
                onResult = { uri ->
                    uri?.let {
                        imageUri = it
                    }
                })

        Column {
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(36.dp)
                )
            }

            SimpleTextLink(
                name = "Pick image"
            ) {
                galleryLauncher.launch("image/*")
            }
        }
    }
}

