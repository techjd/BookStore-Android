package com.techjd.bookstore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.techjd.bookstore.db.converter.Converter
import com.techjd.bookstore.db.dao.CartDao
import com.techjd.bookstore.db.models.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
}