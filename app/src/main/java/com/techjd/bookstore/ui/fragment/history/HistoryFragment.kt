package com.techjd.bookstore.ui.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentHistoryBinding
import com.techjd.bookstore.models.allOrders.AllOrder
import com.techjd.bookstore.models.allOrders.OrderItem
import com.techjd.bookstore.ui.fragment.adapter.HistoryAndOrdersAdapter
import com.techjd.bookstore.ui.fragment.orders.OrdersData
import com.techjd.bookstore.utils.DialogClass
import com.techjd.bookstore.utils.Status
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.BuyerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val buyerViewModel: BuyerViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var glide: RequestManager

    private lateinit var historyAndOrdersAdapter: HistoryAndOrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buyerViewModel.getHistory(
            tokenManager.getTokenWithBearer()!!
        )

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_historyFragment_to_profileFragment)
                }
            })

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        historyAndOrdersAdapter = HistoryAndOrdersAdapter(glide)
        buyerViewModel.history.observe(viewLifecycleOwner) { history ->
            when (history.status) {
                Status.SUCCESS -> {
                    val data = getDataForRecyclerView(history.data!!)
                    historyAndOrdersAdapter.ordersAndHistory = data
                    binding.recyclerView.adapter = historyAndOrdersAdapter
                    hideProgressBar()
                }
                Status.LOADING -> {
                    showProgressBar()
                }
                Status.ERROR -> {
                    hideProgressBar()
                    DialogClass(view).showDialog(history.message!!)
                }
            }
        }
    }

    private fun getDataForRecyclerView(history: AllOrder): MutableList<OrdersData> {
        var recyclerViewData: MutableList<OrdersData> = mutableListOf()

        for (orders in history.data) {
            var singleItem = orders
            for (book in singleItem.orderItems) {
                recyclerViewData.add(
                    OrdersData(
                        status = singleItem.status,
                        sellerName = "${singleItem.seller.firstName} ${singleItem.seller.lastName}",
                        orderItem = book,
                        paymentStatus = getPaymentStatus(singleItem.isPaid)
                    )
                )
            }
        }

        return recyclerViewData
    }

    private fun getPaymentStatus(isPaid: Boolean): String {
        if (isPaid) {
            return "Paid"
        }
        return "UnPaid"
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}