package com.example.financetracker.repository

import com.example.financetracker.data.Category
import com.example.financetracker.data.CategoryDao
import com.example.financetracker.data.Transaction
import com.example.financetracker.data.TransactionDao

class FinanceRepository(private val transactionDao: TransactionDao, private val categoryDao: CategoryDao) {
    fun getAllTransactions() = transactionDao.getAllTransactions()
    fun getAllCategories() = categoryDao.getAllCategories()
}

