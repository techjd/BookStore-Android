package com.techjd.bookstore.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.techjd.bookstore.MainActivity
import com.techjd.bookstore.databinding.FragmentHomeBinding
import com.techjd.bookstore.models.books.Books
import com.techjd.bookstore.models.books.Data
import com.techjd.bookstore.ui.fragment.adapter.BooksAdapter
import com.techjd.bookstore.utils.BottomSheetOptions
import com.techjd.bookstore.utils.Status
import com.techjd.bookstore.viewmodels.BuyerViewModel
import com.techjd.bookstore.viewmodels.ModalBottomSheetViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    val TAG = "HOME FRAGMENT"

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val buyerViewModel: BuyerViewModel by viewModels()
    private val bottomSheetViewModel: ModalBottomSheetViewModel by activityViewModels()
    private lateinit var booksAdapter: BooksAdapter

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var bottomSheet: ModalBottomSheetSort

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).binding.bottomNavigation.visibility = View.VISIBLE

        binding.sort.setOnClickListener {
            bottomSheet.show(parentFragmentManager, "BOTTOM SHEET")
        }

        booksAdapter = BooksAdapter(
            glide
        )

        binding.booksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.booksRecyclerView.setHasFixedSize(true)

        buyerViewModel.getAllBooks()

        bottomSheetViewModel.clickedItem.observe(viewLifecycleOwner) { option ->
            when (option) {
                BottomSheetOptions.ATOZ -> {
                    Log.d(TAG, "onViewCreated: ${option.name}")
                    sortFromAtoZ()
                }
                BottomSheetOptions.ZTOA -> {
                    Log.d(TAG, "onViewCreated: ${option.name}")
                    sortFromZtoA()
                }
                BottomSheetOptions.HTOL -> {
                    Log.d(TAG, "onViewCreated: ${option.name}")
                    sortFromHighToLow()
                }
                BottomSheetOptions.LTOH -> {
                    Log.d(TAG, "onViewCreated: ${option.name}")
                    sortFromLowToHigh()
                }
            }
        }

        buyerViewModel.allBooks.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    enableAllButtons()
                    hideProgressBar()
                    booksAdapter.books = result.data!!.data as MutableList<Data>
                    binding.booksRecyclerView.adapter = booksAdapter
                }
                Status.ERROR -> {
                    enableAllButtons()
                    hideProgressBar()
                }
                Status.LOADING -> {
                    disableAllButtons()
                    showProgressBar()
                }
            }
        }
    }

    fun sortFromHighToLow() {
        booksAdapter.sortFromHighToLow()
    }

    private fun sortFromLowToHigh() {
        booksAdapter.sortFromLowToHigh()
    }

    private fun sortFromZtoA() {
        booksAdapter.sortFromZtoA()
    }

    fun sortFromAtoZ() {
        booksAdapter.sortFromAtoZ()
    }

    private fun disableAllButtons() {
        binding.sort.isClickable = false
//        binding.filter.isClickable = false
    }

    private fun enableAllButtons() {
        binding.sort.isClickable = true
//        binding.filter.isClickable = true
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}