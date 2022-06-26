package com.techjd.bookstore.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.techjd.bookstore.MainActivity
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentSignInBinding
import com.techjd.bookstore.databinding.FragmentSignUpBinding
import com.techjd.bookstore.models.login.request.UserRequestLogin
import com.techjd.bookstore.models.register.request.UserRequestRegister
import com.techjd.bookstore.utils.DialogClass
import com.techjd.bookstore.utils.Status
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUp.setOnClickListener {
            val id = binding.radioGroup.checkedRadioButtonId
            val radioBtn = binding.root.findViewById<RadioButton>(id)
            var role = with(radioBtn) {
                if (radioBtn.text == "Buyer") {
                    return@with "buyer"
                }
                return@with "seller"
            }
            Log.d("INFO", "onViewCreated: ${UserRequestRegister(
                email = binding.emailAddress.text.toString(),
                password = binding.password.text.toString(),
                firstName = binding.firstName.text.toString(),
                lastName = binding.lastName.text.toString(),
                role = role
            )}")
            userViewModel.signUp(
                UserRequestRegister(
                    email = binding.emailAddress.text.toString(),
                    password = binding.password.text.toString(),
                    firstName = binding.firstName.text.toString(),
                    lastName = binding.lastName.text.toString(),
                    role = role
                )
            )
        }

        userViewModel.userRegisterResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    enableAllButtons()
                    tokenManager.saveToken(result.data!!.data.token)
                    findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
                    (activity as MainActivity).setStartDestinationAsHomeFragment()
                }
                Status.ERROR -> {
                    hideProgressBar()
                    enableAllButtons()
                    DialogClass(view).showDialog(result.message!!)
                }
                Status.LOADING -> {
                    showProgressBar()
                    disableAllButtons()
                }
            }
        }


//        binding.signUp.setOnClickListener {
//            findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
//            (activity as MainActivity).setStartDestinationAsHomeFragment()
//        }

        binding.alreadyHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun disableAllButtons() {
        binding.signUp.isClickable = false
        binding.alreadyHaveAnAccount.isClickable = false
    }

    private fun enableAllButtons() {
        binding.signUp.isClickable = true
        binding.alreadyHaveAnAccount.isClickable = true
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}