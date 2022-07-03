package com.techjd.bookstore.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentCartBinding
import com.techjd.bookstore.db.models.Data
import com.techjd.bookstore.ui.fragment.adapter.CartAdapter
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.BuyerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : Fragment() {

    val TAG = "CART FRAGMENT"

    private val buyerViewModel: BuyerViewModel by viewModels()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity as MainActivity).binding.bottomNavigation.visibility = View.VISIBLE


        buyerViewModel.totalAmount.observe(viewLifecycleOwner) { price ->
            Log.d("PRICE ", "onViewCreated: ${price}")
            if (price != null) {
                binding.totalAmout.text = "₹ ${price.toString()}"
            } else {
                binding.totalAmout.text = "₹ 0"
                tokenManager.saveSellerIdofCart("")
            }
        }

        binding.cartRecyclerview.layoutManager = LinearLayoutManager(requireContext())

        cartAdapter = CartAdapter(
            glide,
            increase = { book ->
                book.quantity += 1
                buyerViewModel.updateBook(book)
            },
            decrease = { book ->
                if (book.quantity - 1 == 0) {
                    buyerViewModel.deleteBook(book)
                } else {
                    book.quantity -= 1
                    buyerViewModel.updateBook(book)
                }
            }
        )

        buyerViewModel.allBooksFromCart.observe(viewLifecycleOwner) { data ->
            Log.d(TAG, "DATA ${data}")
            if (data != null) {
                if (data.isEmpty()) {
                    binding.noItems.visibility = View.VISIBLE
                    binding.cartRecyclerview.visibility = View.INVISIBLE
                } else {
                    cartAdapter.books = data as MutableList<Data>
                    binding.cartRecyclerview.visibility = View.VISIBLE
                    binding.noItems.visibility = View.GONE
                    with(binding.cartRecyclerview) {
                        adapter = cartAdapter
                    }
                }
            } else {
                binding.cartRecyclerview.visibility = View.INVISIBLE
                binding.noItems.visibility = View.VISIBLE
            }
        }

        binding.checkout.setOnClickListener {
            if (binding.totalAmout.text.toString() == "₹ 0") {
                showSnackBar("Please Add Few Items in Cart").show()
            } else {
                findNavController().navigate(R.id.action_cartFragment_to_checkOutFragment)
            }
        }
    }

    private fun showSnackBar(message: String): Snackbar {
        return Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        )
    }

}