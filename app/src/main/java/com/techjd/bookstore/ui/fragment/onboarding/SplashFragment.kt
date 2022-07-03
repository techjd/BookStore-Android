package com.techjd.bookstore.ui.fragment.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.techjd.bookstore.ui.activity.MainActivity
import com.techjd.bookstore.R
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject
    lateinit var tokenManager: TokenManager

    private val stateViewModel: StateViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1500L)
            if (tokenManager.getToken() != null) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                (activity as MainActivity).setStartDestinationAsHomeFragment()
//                (activity as MainActivity).binding.bottomNavigation.visibility = View.INVISIBLE
//                stateViewModel.showBottomNavigation.postValue(true)
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            }
        }
    }

}