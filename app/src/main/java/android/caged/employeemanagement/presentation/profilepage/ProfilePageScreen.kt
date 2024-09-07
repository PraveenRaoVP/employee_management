package android.caged.employeemanagement.presentation.profilepage

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.launch

@Composable
fun ProfilePageScreen(
    user: Employee,
    teamName: String,
    onEvent: (ProfilePageEvent) -> Unit,
    state: ProfilePageState
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val imageUrl = remember { mutableStateOf(user.profileImageUrl) }
    val isLoading = remember { mutableStateOf(true) }


    onEvent(ProfilePageEvent.SetUserId(user.employeeId.toLong()))
    onEvent(ProfilePageEvent.SetProfileImageUrl(user.profileImageUrl))

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val newProfileImageUrl = it.toString()

                // Update profile image URL in the Room database
                coroutineScope.launch {
                    onEvent(ProfilePageEvent.UpdateProfileImageUrl(newProfileImageUrl))
                    imageUrl.value = newProfileImageUrl
                    isLoading.value = true // Show progress while loading new image
                }
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading.value) {
            CircularProgressIndicator()
        }
        AndroidView(
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    Glide.with(context)
                        .load(state.profileImageUrl)
                        .circleCrop()
                        .listener(object : RequestListener<Drawable> {
                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                isLoading.value = false
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.e("Glide", "Image load failed", e)
                                isLoading.value = false
                                return false
                            }
                        })
                        .into(this)
                }
            },
            modifier = Modifier
                .height(128.dp)
                .clickable {
                    // Trigger the image picker when the image is clicked
                    imagePickerLauncher.launch("image/*")
                }
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(50.dp)
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display Employee Information
        Text(text = "Name: ${user.employeeName}")
        Text(text = "Email: ${user.email}")
        Text(text = "Phone: ${user.phone}")
        Text(text = "Position: ${user.position}")
        Text(text = "Team: $teamName")
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePageScreenPreview() {
    ProfilePageScreen(
        user = Employee(
            employeeId = 1,
            employeeName = "John Doe",
            position = Position.ADMIN,
            designation = "Software Engineer",
            salary = 1000.0,
            teamID = 1,
            email = "dawoudnwoduinwa",
            phone = "1234567890",
            profileImageUrl = "https://www.google.com"
        ),
        teamName = "Team A",
        onEvent = {},
        state = ProfilePageState()
    )
}