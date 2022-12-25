package com.techjd.bookstore.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.techjd.bookstore.db.models.Data

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Data)

    @Query("SELECT * FROM cart")
    fun getAllBooks(): LiveData<List<Data>>

    @Delete
    suspend fun deleteBook(book: Data)

    @Update
    suspend fun updateBook(book: Data)

    @Query("SELECT EXISTS (SELECT 1 FROM cart WHERE id = :id)")
    suspend fun checkIfItemExists(id: String): Boolean

    @Query("SELECT SUM(quantity * price) FROM cart")
    fun getTotal(): LiveData<Int>

    @Query("SELECT COUNT(id) FROM cart")
    fun getCountInCart(): LiveData<Int>

    @Query("SELECT COUNT(id) FROM cart")
    suspend fun getCartCount(): Int

    @Query("SELECT * FROM CART LIMIT 1")
    suspend fun checkIfUserIsInsertingBooksWithSameSeller(): Data

    @Query("DELETE FROM cart")
    suspend fun deleteAll(): Int
}