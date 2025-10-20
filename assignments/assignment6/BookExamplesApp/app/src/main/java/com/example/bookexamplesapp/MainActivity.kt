package com.example.bookexamplesapp

import android.R.attr.onClick
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.core.view.WindowCompat

// Compose UI imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.TextButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Navigation imports
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        setContent {
            MaterialTheme {
                Surface {
                    LoginApp()
                }
            }
        }
    }
}

@Composable
fun LoginApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToProfile = {
                    navController.navigate("profile") {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable("Welcome") {
            WelcomeScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("Profile") {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun LoginScreen() {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(true) }
    var emailError by remember { mutableStateOf(true) }
    var passwordError by remember { mutableStateOf(true) }

    var passwordVisible by remember { mutableStateOf(true) }
    var validEmail by remember { mutableStateOf("student@wccnet.edu") }
    var validPassword by remember { mutableStateOf("password123") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Student Login",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fontWeight(FontWeight.Bold)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {


            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    placeholder = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !nameError,
                    supportingText = {
                        if (!nameError) {
                            Text("Please enter your Name")
                        }
                    }
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    placeholder = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !emailError,
                    supportingText = {
                        if (!emailError) {
                            Text("Please enter your Email Address")
                        }
                    }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    placeholder = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !passwordError,
                    supportingText = {
                        if (!passwordError) {
                            Text("Please enter your Password")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Handle registration */ },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = nameError &&
                            emailError &&
                            passwordError &&
                            name.isNotEmpty() &&
                            email.isNotEmpty()
                ) {
                    Text("Login")
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome!",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fontWeight(FontWeight.Bold)
        )

        Text("Hello",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fontWeight(FontWeight.Bold)
        )

        Button(onClick = navController.navigate(ProfileScreen())) {
            Text("View Profile")
        }

        Button(onClick = navController.navigate(LoginScreen())) {
            Text("Logout")
        }
    }
}

fun Modifier.Companion.fontWeight(bold: FontWeight): Modifier {}

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "User Profile",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fontWeight(FontWeight.Bold)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ProfileRow()
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = navController.navigate(WelcomeScreen())) {
                Text("Back to Welcome")
            }
        }
    }
}

@Composable
fun ProfileRow() {
    Row (modifier = Modifier.fontWeight(FontWeight.Bold),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex(
        "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}"
    )
    return if (email.matches(emailRegex)) {
        email.matches(emailRegex)
    } else {
        false
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onLoginSuccess = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}
