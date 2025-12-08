package com.example.financetracker.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.financetracker.data.Category
import com.example.financetracker.data.FinanceDatabase
import com.example.financetracker.data.Transaction
import com.example.financetracker.repository.FinanceRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class FinanceViewModel(private val repository: FinanceRepository) : ViewModel() {

    val allTransactions: StateFlow<List<Transaction>> = repository.getAllTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    val allCategories: StateFlow<List<Category>> = repository.getAllCategories()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun insertCategory(category: Category) {
        viewModelScope.launch {
            //repository.insertCategory(category)
        }
    }

    fun addTransaction(type: String, amount: Double, category: String, description: String, date: Date) {
        viewModelScope.launch {
            val newTransaction = Transaction(
                type = type,
                amount = amount,
                category = category,
                description = description,
                date = date,
                categoryId = 0
            )
            //repository.insertTransaction(newTransaction)
        }
    }

    companion object {
        fun provideFactory(
            app: Application,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = FinanceDatabase.getDatabase(app)
                val repo = FinanceRepository(
                    database.transactionDao(),
                    database.categoryDao()
                )
                return FinanceViewModel(repo) as T
            }
        }
    }
}
