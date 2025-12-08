package com.example.financetracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import com.example.financetracker.viewmodel.FinanceViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenApp(
    viewModel: FinanceViewModel? = null,
    onNavigateToTransactions: () -> Unit = {},
    onNavigateToCategories: () -> Unit = {}
) {
    var balance by remember { mutableStateOf(0) }
    var income by remember { mutableStateOf(0) }
    var expense by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Finance Tracker")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .height(100.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                ) {
                    Column {
                        if (balance < 0) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Warning: Negative Balance",
                                tint = MaterialTheme.colorScheme.error
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Balance is positive",
                                tint = Color(0xFF2BBD2B)
                            )
                        }

                        Text(text = "Current Balance",
                            style = MaterialTheme.typography.labelLarge)
                        Text(text = "$$balance",    style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .width(165.dp)
                        .height(80.dp)
                        .background(
                            color = Color(0xE62BBD2B),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                ) {
                    Column {
                        Text(text = "Income", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "$$income", style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold))
                    }
                }

                Box(
                    modifier = Modifier
                        .width(165.dp)
                        .height(80.dp)
                        .background(
                            color = Color(0xE6F57369),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                ) {
                    Column {
                        Text(text = "Expense", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "$$expense", style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold))
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp,)
            ) {
                Text("Quick Actions", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = onNavigateToTransactions,
                    modifier = Modifier
                        .width(165.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = "Transactions",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Button(
                    onClick = onNavigateToCategories,
                    modifier = Modifier
                        .width(165.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp,)
            ) {
                Text("Recent Transactions (Most Recent 5)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold))
                Text("View All",
                    modifier = Modifier.clickable(onClick = onNavigateToTransactions),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinanceTrackerTheme() {
        HomeScreenApp()
    }
}
