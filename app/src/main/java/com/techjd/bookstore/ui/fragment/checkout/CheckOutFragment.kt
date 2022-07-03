package com.techjd.bookstore.ui.fragment.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.techjd.bookstore.ui.activity.MainActivity
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentCheckOutBinding
import com.techjd.bookstore.db.models.Data
import com.techjd.bookstore.models.orders.request.OrderItem
import com.techjd.bookstore.models.orders.request.OrdersRequest
import com.techjd.bookstore.utils.DialogClass
import com.techjd.bookstore.utils.Status
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.BuyerViewModel
import com.techjd.bookstore.viewmodels.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CheckOutFragment : Fragment() {
    private var _binding: FragmentCheckOutBinding? = null
    private val binding get() = _binding!!
    private val buyerViewModel: BuyerViewModel by viewModels()
    private val stateViewModel: StateViewModel by activityViewModels()
    private lateinit var checkoutAdapter: CheckoutAdapter

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var tokenManager: TokenManager

    private lateinit var totalPrice: Integer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.checkOutRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        checkoutAdapter = CheckoutAdapter(
            glide
        )

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_checkOutFragment_to_cartFragment)
                }
            })

        buyerViewModel.totalAmount.observe(viewLifecycleOwner) { price ->
            Log.d("PRICE ", "onViewCreated: ${price}")
            if (price != null) {
                binding.totalAmount.text = "₹ ${price.toString()}"
                totalPrice = Integer(price)
            } else {
                binding.totalAmount.text = (0).toString()
            }
        }

        buyerViewModel.getAddress("Bearer ${tokenManager.getToken()!!}")

        buyerViewModel.address.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.address.setText(result?.data?.data?.address.toString())
                    hideProgressBar()
                }
                Status.LOADING -> {
                    showProgressBar()
                }
                Status.ERROR -> {
                    DialogClass(view).showDialog(result.message!!)
                    hideProgressBar()
                }
            }
        }

        buyerViewModel.allBooksFromCart.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                if (data.isEmpty()) {
                    binding.checkOutRecyclerView.visibility = View.INVISIBLE
                } else {
                    checkoutAdapter.books = data as MutableList<Data>
                    binding.checkOutRecyclerView.visibility = View.VISIBLE
                    with(binding.checkOutRecyclerView) {
                        adapter = checkoutAdapter
                    }
                }
            } else {
                binding.checkOutRecyclerView.visibility = View.INVISIBLE
            }
        }

        binding.pay.setOnClickListener {
            val id = binding.radioGroup.checkedRadioButtonId
            val radioBtn = binding.root.findViewById<RadioButton>(id)
            val paymentMode = with(radioBtn) {
                if (radioBtn.text == getString(R.string.cash_on_delivery)) {
                    return@with "cod"
                }
                return@with "online"
            }
            val orderItems = getOrderItems(checkoutAdapter.books)

            if (paymentMode == "cod") {

                buyerViewModel.placeOrder(
                    tokenManager.getTokenWithBearer()!!,
                    ordersRequest = OrdersRequest(
                        address = binding.address.text.toString(),
                        seller = checkoutAdapter.books[0].sellerId._id,
                        totalPrice = totalPrice.toInt(),
                        orderItems = orderItems,
                        paymentMode = "cod"
                    )
                )

            } else {
                buyerViewModel.placeOrder(
                    tokenManager.getTokenWithBearer()!!,
                    ordersRequest = OrdersRequest(
                        address = binding.address.text.toString(),
                        seller = checkoutAdapter.books[0].sellerId._id,
                        totalPrice = totalPrice.toInt(),
                        orderItems = orderItems,
                        paymentMode = "online"
                    )
                )
            }
        }

        buyerViewModel.orders.observe(viewLifecycleOwner) { orders ->
            when (orders.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    if (orders.data?.data?.order?.paymentMode == "cod") {
                        val bundle = Bundle()
                        bundle.putString("orderId", "")
                        bundle.putString("status", "success")
                        bundle.putString("mode", "cod")
                        bundle.putString("address", orders.data.data.order.address)
                        findNavController().navigate(R.id.action_checkOutFragment_to_paymentStatusFragment, bundle)
                    } else {
                        (activity as MainActivity).startPayment(orders.data?.data?.order!!._id)
                    }
                }
                Status.LOADING -> {
                    showProgressBar()
                }
                Status.ERROR -> {
                    hideProgressBar()
                    DialogClass(view).showDialog(orders.message!!)
                }
            }

        }

//        stateViewModel.
    }

    private fun getOrderItems(books: MutableList<Data>): List<OrderItem> {
        val orderItems = mutableListOf<OrderItem>()
        for (book in books) {
            orderItems.add(
                OrderItem(
                    qty = book.quantity,
                    price = book.price,
                    book = book._id
                )
            )
        }
        return orderItems
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}