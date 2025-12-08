package com.example.financetracker.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financetracker.screens.AddEditTransactionScreenApp
import com.example.financetracker.screens.CategoryManagementScreenApp
import com.example.financetracker.screens.HomeScreenApp
import com.example.financetracker.screens.TransActionListScreenApp
import com.example.financetracker.viewmodel.FinanceViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object TransactionList : Screen("transaction_list")
    object CategoryManagement : Screen("category_management")
    object AddEditTransaction : Screen("add_edit_transaction")
}

@Composable
fun FinanceNavHost(
    navController: NavHostController,
    viewModel: FinanceViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreenApp(
                viewModel = viewModel,
                onNavigateToTransactions = { navController.navigate(Screen.TransactionList.route) },
                onNavigateToCategories = { navController.navigate(Screen.CategoryManagement.route) }
            )
        }
        composable(Screen.TransactionList.route) {
            val application = LocalContext.current.applicationContext as Application
            val viewModel: FinanceViewModel = viewModel(
                 factory = FinanceViewModel.provideFactory(application)
            )

            TransActionListScreenApp(
                viewModel = viewModel,
                onNavigateBack = { navigateBack(navController) },
                onNavigateToAddEditTransaction = { navController.navigate(Screen.AddEditTransaction.route) }
            )
        }
        composable(Screen.CategoryManagement.route) {
            CategoryManagementScreenApp(
                viewModel = viewModel,
                onNavigateBack = { navigateBack(navController) }
            )
        }
        composable(Screen.AddEditTransaction.route) {
            AddEditTransactionScreenApp(
                viewModel = viewModel,
                onNavigateBack = { navigateBack(navController) }
            )
        }
    }
}

fun navigateBack(navController: NavHostController) {
    navController.navigateUp()
}
