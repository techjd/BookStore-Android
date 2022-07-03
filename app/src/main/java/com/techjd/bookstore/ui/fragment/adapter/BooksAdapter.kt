package com.techjd.bookstore.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.android.material.button.MaterialButton
import com.techjd.bookstore.R
import com.techjd.bookstore.db.models.CategoryId
import com.techjd.bookstore.db.models.SellerId
import com.techjd.bookstore.models.books.Books
import com.techjd.bookstore.models.books.Data
import okhttp3.internal.filterList
import javax.inject.Inject

class BooksAdapter(
    private val glide: RequestManager,
    private val addToCart: (book: com.techjd.bookstore.db.models.Data) -> Unit
) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    var books: MutableList<Data> = mutableListOf()

    class BooksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val author: TextView = itemView.findViewById(R.id.author)
        val description: TextView = itemView.findViewById(R.id.description)
        val price: TextView = itemView.findViewById(R.id.price)
        val soldBy: TextView = itemView.findViewById(R.id.soldBy)
        val addToCart: MaterialButton = itemView.findViewById(R.id.addToCart)
        val bookImage: ImageView = itemView.findViewById(R.id.bookImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BooksViewHolder(view)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val book = books[position]

        with(book) {
            holder.bookTitle.text = title
            holder.author.text = author
            holder.description.text = description
            holder.price.text = price.toString()
            val sellerName = "${sellerId.firstName} ${sellerId.lastName}"
            holder.soldBy.text = sellerName
            glide.load(book.imageUrl).into(holder.bookImage)
        }

        holder.addToCart.setOnClickListener {
            addToCart.invoke(
                com.techjd.bookstore.db.models.Data(
                    __v = book.__v,
                    _id = book._id,
                    author = book.author,
                    categoryId = CategoryId(
                        book.categoryId._id,
                        book.categoryId.categoryName
                    ),
                    createdAt = book.createdAt,
                    updatedAt = book.updatedAt,
                    title = book.title,
                    sellerId = SellerId(
                        book.sellerId._id,
                        book.sellerId.email,
                        book.sellerId.firstName,
                        book.sellerId.lastName
                    ),
                    price = book.price,
                    imageUrl = book.imageUrl,
                    description = book.description
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun sortFromHighToLow() {
        val newList = books.sortedByDescending {
            it.price
        }
        books.clear()
        books.addAll(newList)
        notifyDataSetChanged()
    }

    fun sortFromLowToHigh() {
        val newList = books.sortedBy {
            it.price
        }
        books.clear()
        books.addAll(newList)
        notifyDataSetChanged()
    }

    fun sortFromZtoA() {
        val newList = books.sortedByDescending {
            it.title
        }
        books.clear()
        books.addAll(newList)
        notifyDataSetChanged()
    }

    fun sortFromAtoZ() {
        val newList = books.sortedBy {
            it.title
        }
        books.clear()
        books.addAll(newList)
        notifyDataSetChanged()
    }
}