package com.techjd.bookstore.ui.fragment.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.techjd.bookstore.databinding.ItemLayoutCartBinding
import com.techjd.bookstore.databinding.ItemLayoutCartItemBinding

class CheckoutAdapter(
    private val glide: RequestManager
) : RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    var books: MutableList<com.techjd.bookstore.db.models.Data> = mutableListOf()

    inner class CheckoutViewHolder(val itemBinding: ItemLayoutCartBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val binding =
            ItemLayoutCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
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
                itemBinding.quantity.text = "X ${book.quantity}"
                itemBinding.soldBy.text = sellerName
            }
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}