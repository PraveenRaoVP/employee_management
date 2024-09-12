package android.caged.employeemanagement.presentation.employeedetails

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.presentation.common.EmployeeDetail
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
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
    Scaffold(

    ) { innerPadding ->
        val topPadding = innerPadding.calculateTopPadding()
        employee?.let { emp ->
            EmployeeDetail(emp, teamName)
        } ?: run {
            Text(text = "Loading employee details...")
        }
    }
}

