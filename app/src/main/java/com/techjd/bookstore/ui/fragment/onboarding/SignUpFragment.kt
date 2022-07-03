package com.techjd.bookstore.ui.fragment.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.techjd.bookstore.ui.activity.MainActivity
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentSignUpBinding
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
            if (validate()) {
                userViewModel.signUp(
                    UserRequestRegister(
                        email = binding.emailAddress.text.toString().trim(),
                        password = binding.password.text.toString().trim(),
                        firstName = binding.firstName.text.toString().trim(),
                        lastName = binding.lastName.text.toString().trim(),
                        role = role
                    )
                )
            } else {
                DialogClass(view).showDialog("Please Fill All Fields")
            }
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


        binding.alreadyHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun validate(): Boolean {
        if (binding.emailAddress.text.toString().trim() != ""
            && binding.password.text.toString().trim() != ""
            && binding.firstName.text.toString().trim() != ""
            && binding.lastName.text.toString().trim() != ""
        ) {
            return true
        }
        return false
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