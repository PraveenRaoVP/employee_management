package android.caged.employeemanagement.presentation.home

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team
import android.caged.employeemanagement.presentation.common.EmployeeList
import android.caged.employeemanagement.presentation.navgraph.Screen
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot


@Composable
fun HomeScreen(
    navigateToPopUp: (String, String) -> Unit,
    navigateTo: (String) -> Unit,
    currentUser: Employee,
    state: HomeState,
    navigate: (Employee) -> Unit,
    onRefetchData: () -> Unit
) {
    LaunchedEffect(Unit) {
        onRefetchData()
    }

    if (currentUser.position == Position.ADMIN) {
        AdminDashboardScreen(
            employeeCount = state.employeeCount,
            teamCount = state.teamCount,
            recentEmployees = state.recentEmployees,
            teamMap = state.teamMap,
            navigate = navigate,
            state = state,
            onRefetchData = onRefetchData
        )
    }
    if (currentUser.position == Position.MANAGER) {
        ManagerScreen(
            recentEmployees = state.recentEmployees,
            teamMap = state.teamMap,
            navigate = navigate,
            currentUser = currentUser
        )
    }

    if (currentUser.position == Position.EMPLOYEE) {
        EmployeeHomeScreen(currentUser, navigateTo)
    }
}

@Composable
fun AdminDashboardScreen(
    employeeCount: Int,
    teamCount: Int,
    recentEmployees: List<Employee>,
    teamMap: Map<Long, Team>,
    navigate: (Employee) -> Unit,
    state: HomeState,
    onRefetchData: () -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .scrollable(rememberScrollState(), Orientation.Vertical),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header section
            Text("Dashboard", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Total Employees: $employeeCount", style = MaterialTheme.typography.bodyLarge)
            Text("Total Teams: $teamCount", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text("New Employees Joined", style = MaterialTheme.typography.titleMedium)

            // List of new employees
            EmployeeList(
                employeeList = state.recentEmployees,
                teamMap = teamMap,
                onEmployeeClick = navigate,
                onDeleteClicked = { onRefetchData() }
            )

//            BarChart(state = state, maxHeight = 240.dp)
        }
    }
}

//@Composable
//fun BarChart(state: HomeState, maxHeight: Dp) {
////    val context = LocalContext.current
////    val dataPoints = state.employeeCountPerTeam.map { (team, employeeCount) ->
////        DataPoint(
////            x = team.teamID.toFloat(),
////            y = employeeCount.toFloat()
////        )
////    }
////    LineGraph(plot = LinePlot(
////        listOf(
////            LinePlot.Line(
////                dataPoints = dataPoints,
////                LinePlot.Connection(color = Color.Red),
////                LinePlot.Intersection(color = Color.Red),
////                LinePlot.Highlight(color = Color.Yellow)
////            ),
////        ),
////        grid = LinePlot.Grid(Color.LightGray, steps = 4),
////    ),
////        modifier = Modifier
////            .fillMaxWidth()
////            .height(200.dp),
////        onSelection = { xLine, points ->
////            Toast.makeText(context, "Selected $xLine", Toast.LENGTH_SHORT).show()
////        })
//
//
//    val context = LocalContext.current
//    // BarGraph Dimensions
//    val barGraphHeight = maxHeight
//    val barGraphWidth by remember { mutableStateOf(20.dp) }
//    // Scale Dimensions
//    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
//    val scaleLineWidth by remember { mutableStateOf(2.dp) }
//
//    val isDarkTheme = isSystemInDarkTheme()
//    val barColor = if (isDarkTheme) Color.Cyan else Color.Red
//    val backgroundColor = if (isDarkTheme) Color.DarkGray else Color.White
//    val textColor = if (isDarkTheme) Color.White else Color.Black
//
//    Column(
//        modifier = Modifier
//            .padding(50.dp)
//            .fillMaxSize()
//            .background(backgroundColor)
//            .height(maxHeight),
//        verticalArrangement = Arrangement.Top
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(barGraphHeight), // Use the barGraphHeight for the row
//            verticalAlignment = Alignment.Bottom,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            Box(
//                modifier = Modifier
//                    .height(maxHeight)
//                    .width(scaleYAxisWidth),
//                contentAlignment = Alignment.BottomCenter
//            ) {
//                Column(
//                    modifier = Modifier.height(maxHeight),
//                    verticalArrangement = Arrangement.Bottom
//                ) {
//                    Text(text = state.employeeCount.toString(), color = textColor)
//                    Spacer(modifier = Modifier.height(maxHeight))
//                }
//                Column(
//                    modifier = Modifier.fillMaxHeight(),
//                    verticalArrangement = Arrangement.Bottom
//                ) {
//                    Text(text = (state.employeeCount / 2).toString(), color = textColor)
//                    Spacer(modifier = Modifier.height(maxHeight * 0.5f))
//                }
//            }
//
//            Box(
//                modifier = Modifier
//                    .height(maxHeight)
//                    .width(scaleLineWidth)
//                    .background(textColor)
//            )
//            state.employeeCountPerTeam.forEach { (team, employeeCount) ->
//                Box(
//                    modifier = Modifier
//                        .padding(start = barGraphWidth, bottom = 5.dp)
//                        .clip(CircleShape)
//                        .width(barGraphWidth)
//                        .height(maxHeight * (employeeCount / state.employeeCount).toFloat())
//                        .background(barColor)
//                        .clickable {
//                            Toast
//                                .makeText(context, employeeCount.toString(), Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                )
//            }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(scaleLineWidth)
//                    .background(textColor)
//            )
//
//            Row(
//                modifier = Modifier
//                    .padding(start = scaleYAxisWidth + barGraphWidth + scaleLineWidth)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
//            ) {
//
//                state.employeeCountPerTeam.forEach { (team, employeeCount) ->
//                    Text(
//                        modifier = Modifier.width(barGraphWidth),
//                        text = team.teamName,
//                        textAlign = TextAlign.Center,
//                        color = textColor
//                    )
//                }
//
//            }
//
//        }
//
//    }
//}

//@Composable
//fun BarChart(state: HomeState, maxHeight: Dp) {
//    val context = LocalContext.current
//
//    val dataPoints = state.employeeCountPerTeam.map { (team, employeeCount) ->
//        DataPoint(
//            x = team.teamID.toFloat(),
//            y = employeeCount.toFloat()
//        )
//    }
//
//    if (dataPoints.isNotEmpty()) {
//        LineGraph(
//            plot = LinePlot(
//                listOf(
//                    LinePlot.Line(
//                        dataPoints = dataPoints,
//                        LinePlot.Connection(color = Color.Red),
//                        LinePlot.Intersection(color = Color.Red),
//                        LinePlot.Highlight(color = Color.Yellow)
//                    ),
//                ),
//                grid = LinePlot.Grid(Color.LightGray, steps = 4),
//            ),
//            modifier = Modifier
//                .fillMaxSize()
//                .height(250.dp),
//            onSelection = { xLine, points ->
//                Toast.makeText(context, "Selected $xLine", Toast.LENGTH_SHORT).show()
//            }
//        )
//    } else {
//        // Show a message or a placeholder if there are no data points
//        Text(
//            text = "No data available for the chart.",
//            modifier = Modifier.padding(16.dp),
//            style = MaterialTheme.typography.bodyMedium,
//            color = Color.Gray
//        )
//    }
//}

@Composable
fun ManagerScreen(
    recentEmployees: List<Employee>,
    teamMap: Map<Long, Team>,
    navigate: (Employee) -> Unit,
    currentUser: Employee
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dashboard", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Team: ${teamMap[currentUser.teamID]?.teamName}",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("New Employees Joined", style = MaterialTheme.typography.titleMedium)
        Log.i("Manager", "Recent Employees: $recentEmployees")

        EmployeeList(employeeList = recentEmployees, teamMap = teamMap, onEmployeeClick = navigate)
    }
}


@Composable
fun EmployeeHomeScreen(
    currentUser: Employee,
    navigateTo: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome ${currentUser.employeeName}!")
    }
}