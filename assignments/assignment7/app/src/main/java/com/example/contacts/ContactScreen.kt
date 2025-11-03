package com.example.contacts

import android.R.attr.name
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ContactScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ContactScreenApp()
            }
        }
    }
}

@Composable
fun ContactScreenApp() {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }

    var isNameValid by remember { mutableStateOf(true) }
    var isPhoneValid by remember { mutableStateOf(true) }


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

        Row {
            Button(
                onClick = {  }
            ) {
                Text("Add")
            }

            Button(
                onClick = {  }
            ) {
                Text("Sort Desc")
            }

            Button(
                onClick = {  }
            ) {
                Text("Sort Asc")
            }
        }

        TextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Search") },
            placeholder = { Text("Search Name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Text("Contacts")
    }
}

fun isPhoneValid(phone: String): Boolean {
    val phoneRegex = Regex("^(\\+\\d{1,3}[- ]?)?\\(?\\d{3}\\)?[- ]?\\d{3}[- ]?\\d{4}$")
    return phoneRegex.matches(phone)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactScreenAppPreview() {
    ContactScreenApp()
}