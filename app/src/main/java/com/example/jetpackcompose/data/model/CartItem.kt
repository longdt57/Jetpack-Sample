package com.example.jetpackcompose.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItem(
    @PrimaryKey
    val itemId: String,

    @ColumnInfo(name = "count")
    val count: Int,
)
