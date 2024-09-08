package android.caged.employeemanagement.presentation.listingemployees

import android.caged.employeemanagement.domain.model.Employee
import androidx.room.Query

sealed interface ListingEvent {
    data class Search(val query: String) : ListingEvent
    data class FilterTeam(val teamID: Long) : ListingEvent
    data class UpdateSearchQuery(val searchQuery: String) : ListingEvent
    data class DeleteEmployee(val employee: Employee) : ListingEvent
}