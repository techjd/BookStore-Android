package com.techjd.bookstore.ui.fragment.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentProfileBinding
import com.techjd.bookstore.databinding.FragmentSearchBinding
import com.techjd.bookstore.models.books.Data
import com.techjd.bookstore.ui.fragment.adapter.BooksAdapter
import com.techjd.bookstore.ui.fragment.adapter.SearchAdapter
import com.techjd.bookstore.utils.DialogClass
import com.techjd.bookstore.utils.Status
import com.techjd.bookstore.viewmodels.BuyerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val buyerViewModel: BuyerViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter

    @Inject
    lateinit var glide: RequestManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.doOnTextChanged { text, start, before, count ->
            searchAdapter.filter.filter(text.toString())
        }

        searchAdapter = SearchAdapter(
            glide,
            addToCart = { data ->
                addToCart(data)
            }
        )

        binding.searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchRecyclerView.setHasFixedSize(true)

        buyerViewModel.getAllBooks()

        buyerViewModel.allBooks.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    enableSearch()
                    with(searchAdapter) {
                        books = result.data!!.data as MutableList<Data>
                        fullList = result.data!!.data as MutableList<Data>
                    }

                    binding.searchRecyclerView.adapter = searchAdapter
                }
                Status.ERROR -> {
                    disableSearch()
                    hideProgressBar()
                    DialogClass(view).showDialog(result.message!!)
                }
                Status.LOADING -> {
                    showProgressBar()
                    disableSearch()
                }
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

    private fun addToCart(book: com.techjd.bookstore.db.models.Data) {
        buyerViewModel.checkIfItemExists(book._id)
        buyerViewModel.doesItemExist.observe(viewLifecycleOwner) { exists ->
            if (exists) {
                showSnackBar("Item Already In Cart").show()
            } else {
                buyerViewModel.insertBook(book)
                showSnackBar("Item Added To Cart").show()
            }
        }
    }

    private fun enableSearch() {
        binding.search.isEnabled = true
    }

    private fun disableSearch() {
        binding.search.isEnabled = false
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}