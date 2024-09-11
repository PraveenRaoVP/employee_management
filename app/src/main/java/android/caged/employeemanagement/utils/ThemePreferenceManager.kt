package android.caged.employeemanagement.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf

object ThemePreferenceManager {
    private const val PREFS_NAME = "employee_management_prefs"
    private const val KEY_IS_DARK_THEME = "is_dark_theme"

    private lateinit var preferences: SharedPreferences

    val isDarkTheme = mutableStateOf(false)

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        isDarkTheme.value = preferences.getBoolean(KEY_IS_DARK_THEME, false)
    }

    fun setDarkTheme(isDark: Boolean) {
        isDarkTheme.value = isDark
        preferences.edit().putBoolean(KEY_IS_DARK_THEME, isDark).apply()
    }
}