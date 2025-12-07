package com.example.financetracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financetracker.screens.AddEditTransactionScreenApp
import com.example.financetracker.screens.CategoryManagementScreenApp
import com.example.financetracker.screens.HomeScreenApp
import com.example.financetracker.screens.TransActionListScreenApp
import com.example.financetracker.viewmodel.FinanceViewModel

/**
 * Defines the navigation routes for the application.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object TransactionList : Screen("transaction_list")
    object CategoryManagement : Screen("category_management")
    object AddEditTransaction : Screen("add_edit_transaction")
}

/**
 * The main navigation host for the application. This composable is responsible for
 * defining the navigation graph.
 *
 * @param navController The NavHostController that will manage navigation.
 * @param viewModel The FinanceViewModel to be shared across screens.
 */
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

/**
 * A helper function to navigate back to the previous screen in the back stack.
 *
 * @param navController The NavController to use for navigation.
 */
fun navigateBack(navController: NavHostController) {
    navController.navigateUp()
}
