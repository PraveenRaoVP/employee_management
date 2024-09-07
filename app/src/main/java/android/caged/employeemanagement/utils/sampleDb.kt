package android.caged.employeemanagement.utils

import android.caged.employeemanagement.domain.model.Employee
import android.caged.employeemanagement.domain.model.Position
import android.caged.employeemanagement.domain.model.Team

val teams = listOf(
    Team(teamID = 1, teamName = "A", teamLeadID = 1),
    Team(teamID = 2, teamName = "B", teamLeadID = 0),
    Team(teamID = 3, teamName = "C" ,teamLeadID = 2)
)

// manager of admin team?????

val employees = listOf(
    Employee(
        employeeId = 0,
        employeeName = "John Doe",
        position = Position.ADMIN,
        designation = "Head Admin",
        teamID = 1,
        salary = 75000.0,
        profileImageUrl = "https://example.com/images/john_doe.jpg",
        email = "johndoe@gmail.com",
        phone = "1234567890"
    ),
    Employee(
        employeeId = 1,
        employeeName = "Jane Boe",
        position = Position.MANAGER,
        designation = "Head Admin",
        teamID = 1,
        salary = 75000.0,
        profileImageUrl = "https://example.com/images/john_doe.jpg",
        email = "johndoe@gmail.com",
        phone = "1234567890"
    ),
    Employee(
        employeeId = 0,
        employeeName = "John Doe",
        position = Position.ADMIN,
        designation = "Head Admin",
        teamID = 1,
        salary = 75000.0,
        profileImageUrl = "https://example.com/images/john_doe.jpg",
        email = "johndoe@gmail.com",
        phone = "1234567890"
    ),
    Employee(
        employeeId = 0,
        employeeName = "John Doe",
        position = Position.ADMIN,
        designation = "Head Admin",
        teamID = 1,
        salary = 75000.0,
        profileImageUrl = "https://example.com/images/john_doe.jpg",
        email = "johndoe@gmail.com",
        phone = "1234567890"
    ),
    Employee(
        employeeId = 0,
        employeeName = "John Doe",
        position = Position.ADMIN,
        designation = "Head Admin",
        teamID = 1,
        salary = 75000.0,
        profileImageUrl = "https://example.com/images/john_doe.jpg",
        email = "johndoe@gmail.com",
        phone = "1234567890"
    ),
    Employee(
        employeeId = 0,
        employeeName = "John Doe",
        position = Position.ADMIN,
        designation = "Head Admin",
        teamID = 1,
        salary = 75000.0,
        profileImageUrl = "https://example.com/images/john_doe.jpg",
        email = "johndoe@gmail.com",
        phone = "1234567890"
    ),
)