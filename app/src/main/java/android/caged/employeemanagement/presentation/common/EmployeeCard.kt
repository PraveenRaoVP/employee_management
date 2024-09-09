package android.caged.employeemanagement.presentation.common

import android.caged.employeemanagement.R
import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmployeeCard(
    modifier: Modifier = Modifier,
    employee: Employee,
    team: Team,
    onClick: () -> Unit,
    showDeleteButton: Boolean = false,
    onDeleteClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Spacer(modifier = Modifier.height(6.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)) // Apply rounded corners
            .clickable { onClick() }
    ) {
        AndroidView(
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    Glide.with(context)
                        .load(employee.profileImageUrl)
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
        Column(
            modifier = Modifier
                .padding(horizontal = 3.dp)
                .height(96.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = "Employee ID: ${employee.employeeId}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = employee.employeeName,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.text_title),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = team.teamName,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = employee.designation,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(id = R.color.body)
                )
            }
        }
        if(showDeleteButton) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red,
                modifier = Modifier.clickable { onDeleteClick() },
            )
        }

        if(employee.position == Position.ADMIN) {
            Icon(imageVector = Icons.Default.Star, contentDescription = "Star Icon for Admin")
        } else if(employee.position == Position.MANAGER) {
            Icon(imageVector = Icons.Default.Face, contentDescription = "Face Icon for Manager")
        } else {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Person Icon for Employee")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmployeeCardPreview() {
    EmployeeCard(
        employee = Employee(
            employeeId = 1,
            employeeName = "John Doe",
            position = Position.ADMIN,
            designation = "Software Engineer",
            salary = 1000.0,
            email = "johndoe@gmail.com",
            phone = "1234567890",
            teamID = 1,
            profileImageUrl = "https://randomuser.me/api/port"
        ),
        team = Team(
            teamID = 1,
            teamName = "Team A",
            teamLeadID = 1
        ), onClick = {},
        showDeleteButton = true,
        onDeleteClick = {}

    )
}