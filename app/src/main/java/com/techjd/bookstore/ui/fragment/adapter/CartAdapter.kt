package com.techjd.bookstore.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.google.android.material.button.MaterialButton
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.ItemLayoutCartItemBinding
import com.techjd.bookstore.db.models.CategoryId
import com.techjd.bookstore.db.models.SellerId
import com.techjd.bookstore.models.books.Data

class CartAdapter(
    private val glide: RequestManager,
    private val increase: (data: com.techjd.bookstore.db.models.Data) -> Unit,
    private val decrease: (data: com.techjd.bookstore.db.models.Data) -> Unit
) : RecyclerView.Adapter<CartAdapter.BooksViewHolder>() {

    var books: MutableList<com.techjd.bookstore.db.models.Data> = mutableListOf()

    inner class BooksViewHolder(val itemBinding: ItemLayoutCartItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val binding =
            ItemLayoutCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val book = books[position]
        with(holder) {
            with(book) {
                itemBinding.bookTitle.text = title
                itemBinding.author.text = author
                itemBinding.description.text = description
                itemBinding.price.text = price.toString()
                val sellerName = "${sellerId.firstName} ${sellerId.lastName}"
                itemBinding.soldBy.text = sellerName
                glide.load(book.imageUrl).into(itemBinding.bookImage)
                itemBinding.quantity.text = book.quantity.toString()
                itemBinding.increase.setOnClickListener {
                    increase.invoke(book)
                }
                itemBinding.decrease.setOnClickListener {
                    decrease.invoke(book)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}