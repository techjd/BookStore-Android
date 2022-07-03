package com.techjd.bookstore.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart")
data class Data(
    @ColumnInfo(name = "_v")
    val __v: Int,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val _id: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int = 1,

    @ColumnInfo(name = "name")
    val author: String,

    @ColumnInfo(name = "category")
    val categoryId: CategoryId,

    @ColumnInfo(name = "createdAt")
    val createdAt: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "seller")
    val sellerId: SellerId,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String
)