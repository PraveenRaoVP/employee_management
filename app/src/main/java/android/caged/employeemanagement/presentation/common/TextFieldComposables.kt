package android.caged.employeemanagement.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import android.caged.employeemanagement.R.drawable as AppIcon

@Composable
fun BasicField(
    text: String,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text) }
    )
}

@Preview(showBackground = true)
@Composable
fun BasicFieldPreview() {
    BasicField("Email", "", {})
}

@Composable
fun NoOutlineField(
    text: String,
    value: String,
    onNewValue: (String) -> Unit,
    modifier : Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onNewValue,
        placeholder = { Text(text) },
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    )
}

//@Preview(showBackground = true)
//@Composable
//fun NoOutlineFieldPreview() {
//    NoOutlineField(AppText.title, "", {})
//}

@Composable
fun EmployeeIDField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier, isError: Boolean) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        isError = isError,
        onValueChange = { onNewValue(it) },
        placeholder = { Text("Employee ID") },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Email") }
    )
}

//@Composable
//fun UsernameField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier, isError: Boolean) {
//    OutlinedTextField(
//        singleLine = true,
//        modifier = modifier,
//        value = value,
//        onValueChange = { onNewValue(it) },
//        isError = isError,
//        placeholder = { Text(stringResource(R.string.username)) },
//        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Username") }
//    )
//}

@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier, isError: Boolean=false) {
    PasswordField(value, "Password", onNewValue, modifier, isError)
}

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, "Repeat Password", onNewValue, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    placeholder: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(AppIcon.ic_visibility_on)
        else painterResource(AppIcon.ic_visibility_off)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}