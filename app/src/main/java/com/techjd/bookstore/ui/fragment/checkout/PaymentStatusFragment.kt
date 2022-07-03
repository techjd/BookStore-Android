package com.techjd.bookstore.ui.fragment.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentPaymentStatusBinding
import com.techjd.bookstore.models.address.request.InputAddress
import com.techjd.bookstore.models.paymentstatus.PaymentStatusRequest
import com.techjd.bookstore.utils.DialogClass
import com.techjd.bookstore.utils.Status
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.BuyerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PaymentStatusFragment : Fragment() {

    private var _binding: FragmentPaymentStatusBinding? = null
    private val binding get() = _binding!!

    private val buyerViewModel: BuyerViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        Log.d("MODE ", "onViewCreated: ${arguments?.get("mode")}")

        if (arguments?.getString("mode") == "cod") {
            binding.animation.setAnimation(
                context?.resources?.getIdentifier(
                    "success",
                    "raw",
                    context!!.packageName
                )!!
            )
            binding.status.text = "Order Successfully Placed \n \n Redirecting ..."
            buyerViewModel.addAddress(
                tokenManager.getTokenWithBearer()!!,
                InputAddress(
                    address = arguments?.getString("address")!!
                )
            )

            buyerViewModel.addAddress.observe(viewLifecycleOwner) { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        lifecycleScope.launch {
                            delay(3000L)
                            findNavController().navigate(R.id.action_paymentStatusFragment_to_homeFragment)
                        }
                    }
                    Status.ERROR -> {
                        lifecycleScope.launch {
                            delay(3000L)
                            findNavController().navigate(R.id.action_paymentStatusFragment_to_homeFragment)
                        }
                    }
                    Status.LOADING -> {

                    }
                }
            }
            lifecycleScope.launch {
                delay(4000L)
                findNavController().navigate(R.id.action_paymentStatusFragment_to_homeFragment)
            }
        } else {
            if (arguments?.getString("status") == "success") {
                val orderId = arguments?.get("orderId").toString()
                binding.animation.setAnimation(
                    context?.resources?.getIdentifier(
                        "success",
                        "raw",
                        context!!.packageName
                    )!!
                )
                binding.status.text =
                    "Payment Successful & \n Order Successfully Placed \n Redirecting You..."
                buyerViewModel.updatePayment(
                    tokenManager.getTokenWithBearer()!!,
                    paymentStatusRequest = PaymentStatusRequest(
                        orderId = orderId
                    )
                )
                buyerViewModel.paymentUpdate.observe(viewLifecycleOwner) { result ->
                    when (result.status) {
                        Status.SUCCESS -> {
                            lifecycleScope.launch {
                                delay(3000L)
                                findNavController().navigate(R.id.action_paymentStatusFragment_to_homeFragment)
                            }
                        }
                        Status.LOADING -> {

                        }
                        Status.ERROR -> {
                            DialogClass(view,
                                onClick = {
                                    findNavController().navigate(R.id.action_paymentStatusFragment_to_homeFragment)
                                }
                            ).showDialogWithOnClick(result.message!!)
                        }
                    }

                }
            } else {
                binding.animation.setAnimation(
                    context?.resources?.getIdentifier(
                        "failure",
                        "raw",
                        context!!.packageName
                    )!!
                )
                binding.status.text = "Payment Failed \n Redirecting ..."
                lifecycleScope.launch {
                    delay(3000L)
                    findNavController().navigate(R.id.action_paymentStatusFragment_to_homeFragment)
                }
            }
        }

    }
}