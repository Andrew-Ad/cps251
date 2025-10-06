package com.example.bookexamplesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * This project covers concepts from Chapter 7 lessons:
 * - "Validation" - for form validation and error handling
 * - "Managing Input State" - for state management in forms
 * - "Text Fields" - for input field styling and error states
 * - "Regular Expressions" - for email, phone, and ZIP code validation
 *
 * Students should review these lessons before starting:
 * - Validation lesson for form validation patterns
 * - Managing Input State lesson for state management
 * - Text Fields lesson for input field styling
 * - Regular Expressions lesson for validation patterns
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactValidatorApp()
            }
        }
    }
}

@Composable
fun ContactValidatorApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Information",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        ContactForm()
    }
}

@Composable
fun ContactForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var zipCode by remember { mutableStateOf("") }

    var isNameValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPhoneValid by remember { mutableStateOf(true) }
    var isZipValid by remember { mutableStateOf(true) }

    var submittedInfo by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
    ),
    verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            placeholder = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isNameValid && name.isNotEmpty(),
            supportingText = {
                if (!isNameValid && name.isNotEmpty()) {
                    Text("Please enter your name")
                }
            }
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isEmailValid && email.isNotEmpty(),
            supportingText = {
                if (!isEmailValid && email.isNotEmpty()) {
                    Text("Please enter your Email")
                }
            }
        )
        
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            placeholder = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPhoneValid && phone.isNotEmpty(),
            supportingText = {
                if (!isPhoneValid && phone.isNotEmpty()) {
                    Text("Please enter your Phone Number")
                }
            }
        )
        
        OutlinedTextField(
            value = zipCode,
            onValueChange = { zipCode = it },
            label = { Text("ZIP code") },
            placeholder = { Text("ZIP code") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isZipValid && zipCode.isNotEmpty(),
            supportingText = {
                if (!isZipValid && zipCode.isNotEmpty()) {
                    Text("Please enter your Zip Code")
                }
            }
        )

        Button(
            onClick = { /* Handle registration */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = isNameValid && isEmailValid && isPhoneValid &&
                    isZipValid && name.isNotEmpty() &&
                    email.isNotEmpty() && phone.isNotEmpty() &&
                    zipCode.isNotEmpty()
        ) {
            Text("Submit")
        }
        if (isNameValid && isEmailValid && isPhoneValid &&
            isZipValid && name.isNotEmpty() &&
            email.isNotEmpty() && phone.isNotEmpty() &&
            zipCode.isNotEmpty()
            ) {
            Text("Name: $name")
            Text("Email: $email")
            Text("Phone Number: $phone")
            Text("Zip Code: $zipCode")
        } else {
            Text("You Need to Enter the Correct Info")
        }
    }
}

@Composable
fun NameField(
    name: String,
    isNameValid: Boolean,
    onValueChange: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    TextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Name") },
        placeholder = { Text("Full Name") }
    )
}

@Composable
fun EmailField(
    email: String,
    isEmailValid: Boolean,
    onValueChange: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    TextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Email") },
        placeholder = { Text("Email") }
    )
}

@Composable
fun PhoneField(
    phone: String,
    isPhoneValid: Boolean,
    onValueChange: (String) -> Unit
) {
    var phone by remember { mutableStateOf("") }
    OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        label = { Text("Phone Number") },
        placeholder = { Text("Phone Number") }
    )
}

@Composable
fun ZipCodeField(
    zipCode: String,
    isZipValid: Boolean,
    onValueChange: (String) -> Unit
) {
    var zipCode by remember { mutableStateOf("") }
    OutlinedTextField(
        value = zipCode,
        onValueChange = { zipCode = it },
        label = { Text("ZIP code") },
        placeholder = { Text("ZIP code") }
    )
}

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-]+@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    )
    return email.matches(emailRegex)
}

fun isPhoneValid(phone: String): Boolean {
    val phoneRegex = Regex("^(\\+\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$")
    return phoneRegex.matches(phone)
}

fun isZipValid(zipCode: String): Boolean {
    val zipCodeRegex = Regex("^\\d{5}(-\\d{4})?$")
    return zipCodeRegex.matches(zipCode)
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactValidatorAppPreview() {
    ContactValidatorApp()
}