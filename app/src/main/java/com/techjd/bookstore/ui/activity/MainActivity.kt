package com.techjd.bookstore.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.techjd.bookstore.R
import com.techjd.bookstore.databinding.ActivityMainBinding
import com.techjd.bookstore.utils.ConnectionLiveData
import com.techjd.bookstore.utils.TokenManager
import com.techjd.bookstore.viewmodels.BuyerViewModel
import com.techjd.bookstore.viewmodels.StateViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PaymentResultWithDataListener {

    val TAG = "MainActivity"

    lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    lateinit var navGraph: NavGraph

    @Inject
    lateinit var tokenManager: TokenManager

    private val buyerViewModel: BuyerViewModel by viewModels()

    private val stateViewModel: StateViewModel by viewModels()

    lateinit var connectionLiveData: ConnectionLiveData

    lateinit var checkout: Checkout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectionLiveData = ConnectionLiveData(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Checkout.preload(applicationContext)

        checkout = Checkout()
        checkout.setKeyID("rzp_test_1BOUviiv3nj09Q")
//        connectionLiveData.observe(this) { isNetworkAvailable ->
//            if (isNetworkAvailable) {
//                Log.d(TAG, "onCreate: Network Available")
////                Toast.makeText(this, "Network Available", Toast.LENGTH_SHORT)
//            } else {
//                navController.navigate(R.id.acttion)
//                Log.d(TAG, "onCreate: Network Not Available")
//
////                Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT)
//            }
//        }

        Log.d(TAG, "onCreate: Called")

        navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


        navGraph = navController.navInflater.inflate(R.navigation.main_nav_graph)

        setStartDestinationAsSplashFragment()

        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.cartFragment)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.splashFragment -> {
                    hideBottomNavigationView()
                }
                R.id.homeFragment -> {
                    showBottomNavigation()
                }
                R.id.cartFragment -> {
                    showBottomNavigation()
                }
                R.id.searchFragment -> {
                    showBottomNavigation()
                }
                R.id.profileFragment -> {
                    showBottomNavigation()
                }
                R.id.signInFragment -> {
                    hideBottomNavigationView()
                }
                R.id.signUpFragment -> {
                    hideBottomNavigationView()
                }
                R.id.checkOutFragment -> {
                    hideBottomNavigationView()
                }
                R.id.ordersFragment -> {
                    hideBottomNavigationView()
                }
                R.id.historyFragment -> {
                    hideBottomNavigationView()
                }
            }
        }

        buyerViewModel.totalItemsInCart.observe(this) { count ->
            if (binding.bottomNavigation.visibility == View.VISIBLE) {
                if (count != null) {
                    badge.isVisible = true
                    badge.number = count
                } else {
                    badge.isVisible = false
                }
            }
//            if (count != null) {
//                badge.isVisible = true
//                badge.number = count
//            } else {
//                badge.isVisible = false
//            }
        }

//        stateViewModel.showBottomNavigation.observe(this) { state ->
//            binding.bottomNavigation.visibility = View.VISIBLE
//        }

    }

    fun setStartDestinationAsHomeFragment() {
        navGraph.setStartDestination(R.id.homeFragment)
        navController.graph = navGraph
    }

    fun setStartDestinationAsSplashFragment() {
        navGraph.setStartDestination(R.id.splashFragment)
        navController.graph = navGraph
    }

    fun showBottomNavigation() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }

    fun hideBottomNavigationView() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun startPayment(id: String) {

        try {


            val options = JSONObject()
            buyerViewModel.orderId.postValue(id)
            options.put("name", "Jaydeepsinh Parmar")
            options.put("description", "Reference No. #123456")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
//            options.put("order_id", "62b5bbf1389cc7b242fa387b") //from response of step 3.

            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", "50000") //pass amount in currency subunits

            options.put("prefill.email", "gaurav.kumar@example.com")
            options.put("prefill.contact", "9988776655")

            checkout.open(this, options)
        } catch (e: Exception) {
            Snackbar.make(
                binding.root,
                "Some Error Occurred Please Try Later ",
                Snackbar.LENGTH_SHORT
            )
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        val orderId = buyerViewModel.orderId.value
        val bundle = Bundle()
        bundle.putString("orderId", orderId)
        bundle.putString("status", "success")
        bundle.putString("mode", "online")
        super.onPostResume()
        navController.navigate(R.id.action_checkOutFragment_to_paymentStatusFragment, bundle)
        Log.d(TAG, "onPaymentSuccess: ")
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        val orderId = buyerViewModel.orderId.value
        val bundle = Bundle()
        bundle.putString("orderId", orderId)
        bundle.putString("status", "failure")
        bundle.putString("mode", "online")
        super.onPostResume()
        navController.navigate(R.id.action_checkOutFragment_to_paymentStatusFragment, bundle)
        Log.d(TAG, "onPaymentError: $p1")
    }
}