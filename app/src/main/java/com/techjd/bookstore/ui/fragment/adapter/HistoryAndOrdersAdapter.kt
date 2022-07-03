package com.techjd.bookstore.ui.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.techjd.bookstore.databinding.ItemLayoutHistoryAndOrdersBinding
import com.techjd.bookstore.models.allOrders.AllOrder
import com.techjd.bookstore.ui.fragment.orders.OrdersData

class HistoryAndOrdersAdapter(
    private val glide: RequestManager
) :
    RecyclerView.Adapter<HistoryAndOrdersAdapter.HistoryAndOrdersViewHolder>() {

    var ordersAndHistory: MutableList<OrdersData> = mutableListOf()

    inner class HistoryAndOrdersViewHolder(val itemLayoutHistoryAndOrdersBinding: ItemLayoutHistoryAndOrdersBinding) :
        RecyclerView.ViewHolder(itemLayoutHistoryAndOrdersBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAndOrdersViewHolder {
        val view = ItemLayoutHistoryAndOrdersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryAndOrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryAndOrdersViewHolder, position: Int) {
        val order = ordersAndHistory[position]

        with(holder) {
            with(order) {
                itemLayoutHistoryAndOrdersBinding.author.text = orderItem.book.author
                itemLayoutHistoryAndOrdersBinding.bookTitle.text = orderItem.book.title
                itemLayoutHistoryAndOrdersBinding.description.text = orderItem.book.description
                itemLayoutHistoryAndOrdersBinding.soldBy.text = sellerName
                itemLayoutHistoryAndOrdersBinding.price.text = orderItem.price.toString()
                itemLayoutHistoryAndOrdersBinding.paymentStatus.text = paymentStatus
                itemLayoutHistoryAndOrdersBinding.quantity.text = "X ${orderItem.qty}"
                glide.load(orderItem.book.imageUrl)
                    .into(itemLayoutHistoryAndOrdersBinding.bookImage)
                itemLayoutHistoryAndOrdersBinding.totalPrice.text =
                    "${orderItem.qty * orderItem.price}"
                itemLayoutHistoryAndOrdersBinding.status.text = status
            }
        }
    }

    override fun getItemCount(): Int {
        return ordersAndHistory.size
    }
}