package com.example.financetracker.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financetracker.data.Transaction
import com.example.financetracker.ui.theme.FinanceTrackerTheme
import com.example.financetracker.viewmodel.FinanceViewModel
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransActionListScreenApp(
    viewModel: FinanceViewModel,
    onNavigateBack: () -> Unit = {},
    onNavigateToAddEditTransaction: () -> Unit = {}
) {
    val transactions by viewModel.allTransactions.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Transactions")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                actions = {
                    IconButton(onClick = onNavigateToAddEditTransaction) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Transaction"
                        )
                    }
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
                .padding(horizontal = 8.dp),
        ) {
//            if (transactions.isEmpty()) {
//                Row(
//                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text("No transactions yet. Tap the '+' to add one!")
//                }
//            } else {
//                LazyColumn(
//                    // Add padding at the top and bottom of the list itself
//                    modifier = Modifier.padding(vertical = 8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(transactions) { transaction ->
//                        TransactionItem(transaction = transaction)
//                    }
//                }
//            }
        }
    }
}

//@Composable
//fun TransactionItem(transaction: Transaction) {
//    val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(transaction.date)
//    val amountColor = if ("Income") Color(0xFF008000) else Color.Red
//    val sign = if ("Income") "+" else "-"
//
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = transaction.category,
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = formattedDate,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//            Text(
//                text = "$sign$${"%.2f".format(transaction.amount)}",
//                style = MaterialTheme.typography.titleLarge,
//                color = amountColor,
//                fontWeight = FontWeight.Bold
//            )
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    val app = LocalContext.current.applicationContext as Application
    val viewModel: FinanceViewModel = viewModel(
        factory = FinanceViewModel.provideFactory(app)
    )
    FinanceTrackerTheme {
        TransActionListScreenApp(viewModel)
    }
}

