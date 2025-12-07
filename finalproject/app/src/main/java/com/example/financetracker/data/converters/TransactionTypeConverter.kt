package com.example.financetracker.data.converters

import androidx.room.TypeConverter
import com.example.financetracker.data.TransactionType

class TransactionTypeConverter {
    @TypeConverter
    fun fromTransactionType(value: TransactionType): String {
        return value.name
    }

    @TypeConverter
    fun toTransactionType(value: String): TransactionType {
        return TransactionType.valueOf(value)
    }
}