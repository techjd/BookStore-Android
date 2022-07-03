package com.techjd.bookstore.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.techjd.bookstore.databinding.FilterBottomSheetBinding
import com.techjd.bookstore.databinding.FragmentSignInBinding
import com.techjd.bookstore.utils.BottomSheetOptions
import com.techjd.bookstore.viewmodels.ModalBottomSheetViewModel
import kotlin.ClassCastException

class ModalBottomSheetSort : BottomSheetDialogFragment() {

    private var _binding: FilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val bottomSheetViewModel: ModalBottomSheetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aToZ.setOnClickListener {
            bottomSheetViewModel.setData(BottomSheetOptions.ATOZ)
            dismiss()
        }

        binding.zToa.setOnClickListener {
            bottomSheetViewModel.setData(BottomSheetOptions.ZTOA)
            dismiss()
        }

        binding.highToLow.setOnClickListener {
            bottomSheetViewModel.setData(BottomSheetOptions.HTOL)
            dismiss()
        }

        binding.lowToHight.setOnClickListener {
            bottomSheetViewModel.setData(BottomSheetOptions.LTOH)
            dismiss()
        }


    }

}