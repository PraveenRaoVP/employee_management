package android.caged.employeemanagement.presentation.employeedetails

import android.caged.employeemanagement.domain.model.Employee
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@Composable
fun EmployeeDetailsScreen(
    employee: Employee?,
    teamName: String,
    onBack: () -> Unit,
    onEvent: (EmployeeDetailsEvent) -> Unit
) {
    employee?.let { emp ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        Glide.with(context)
                            .load(emp.profileImageUrl)
                            .circleCrop()
                            .listener(object : RequestListener<Drawable> {
                                override fun onResourceReady(
                                    resource: Drawable,
                                    model: Any,
                                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                                    dataSource: DataSource,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    return false
                                }

                                override fun onLoadFailed(
                                    e: GlideException?,
                                    model: Any?,
                                    target: Target<Drawable>,
                                    isFirstResource: Boolean
                                ): Boolean {
                                    Log.e("Glide", "Image load failed", e)
                                    return false
                                }
                            })
                            .into(this)
                    }
                },
                modifier = Modifier
                    .height(128.dp)
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(50.dp)
                    )
            )

            // Display the employee details
            Text(text = "Name: ${emp.employeeName}")
            Text(text = "Designation: ${emp.designation}")
            Text(text = "Position: ${emp.position}")
            Text(text = "Team: $teamName")
            Text(text = "Salary: ${emp.salary}")
            Text(text = "Email: ${emp.email}")
            Text(text = "Phone: ${emp.phone}")
        }
    } ?: run {
        Text(text = "Loading employee details...")
    }
}