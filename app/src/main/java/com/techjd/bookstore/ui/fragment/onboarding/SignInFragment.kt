package com.techjd.bookstore.ui.fragment.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.techjd.bookstore.ui.activity.MainActivity
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.FragmentSignInBinding
import com.techjd.bookstore.models.login.request.UserRequestLogin
import com.techjd.bookstore.utils.DialogClass
import com.techjd.bookstore.utils.Status
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
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
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signIn.setOnClickListener {
            if (validate()) {
                userViewModel.signIn(
                    UserRequestLogin(
                        email = binding.emailAddress.text.toString(),
                        password = binding.password.text.toString()
                    )
                )
            } else {
                DialogClass(view).showDialog("Please Fill All Fields")
            }
        }

        userViewModel.userLoginResponse.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    enableAllButtons()
                    tokenManager.saveToken(result.data!!.data.token)
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
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

        binding.dontHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun validate(): Boolean {
        if (binding.emailAddress.text.toString().trim() != ""
            && binding.password.text.toString().trim() != ""
        ) {
            return true
        }
        return false
    }

    private fun disableAllButtons() {
        binding.signIn.isClickable = false
        binding.dontHaveAnAccount.isClickable = false
    }

    private fun enableAllButtons() {
        binding.signIn.isClickable = true
        binding.dontHaveAnAccount.isClickable = true
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}