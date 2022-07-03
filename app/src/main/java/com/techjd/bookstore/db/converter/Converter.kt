package com.techjd.bookstore.db.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.techjd.bookstore.db.models.CategoryId
import com.techjd.bookstore.db.models.SellerId
import java.lang.reflect.Type
import javax.inject.Inject

class Converter {

    @TypeConverter
    fun fromCategory(categoryId: CategoryId): String? {
        val gson = Gson()
        return gson.toJson(categoryId)
    }

    @TypeConverter
    fun fromCategoryString(value: String?): CategoryId? {
        val gson = Gson()
        val listType: Type = object : TypeToken<CategoryId>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromSeller(sellerId: SellerId): String? {
        val gson = Gson()
        return gson.toJson(sellerId)
    }

    @TypeConverter
    fun fromSellerString(value: String?): SellerId? {
        val gson = Gson()
        val listType: Type = object : TypeToken<SellerId>() {}.type
        return gson.fromJson(value, listType)
    }

}