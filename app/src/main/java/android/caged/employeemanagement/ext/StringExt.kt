package android.caged.employeemanagement.ext

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidMobileNumber() : Boolean {
    val indianMobileNumberPattern = "^[6-9]\\d{9}\$".toRegex()
    return this.matches(indianMobileNumberPattern)
}

fun String.isValidPassword() : Boolean {
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$".toRegex()
    return this.matches(passwordPattern)
}