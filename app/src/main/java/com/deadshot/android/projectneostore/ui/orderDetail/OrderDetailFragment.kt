package com.deadshot.android.projectneostore.ui.orderDetail


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentOrderDetailBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.ACCESS_TOKEN
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import com.deadshot.android.projectneostore.utils.toastShort
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class OrderDetailFragment : Fragment(), AuthListener {

    private lateinit var orderDetailViewModel: OrderDetailViewModel
    private lateinit var orderDetailModelFactory: OrderDetailModelFactory
    private lateinit var access_token: String

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        val binding = FragmentOrderDetailBinding.inflate(inflater)

        //load data from shared preferences
        loadData()

        //set lifecyle owner
        binding.lifecycleOwner = this

        val arguments= OrderDetailFragmentArgs.fromBundle(arguments!!)

        orderDetailModelFactory = OrderDetailModelFactory(access_token, arguments.orderId)
        orderDetailViewModel =  ViewModelProviders.of(this , orderDetailModelFactory).get(OrderDetailViewModel::class.java)


        orderDetailViewModel.authListener.value = this
        return binding.root
    }

    /**
     * Load data from shared preferences
     */
    private fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE) ?: return
        access_token = sharedPreferences.getString(ACCESS_TOKEN , getString(R.string.default_value))!!
    }

    override fun onStarted() {
        toastShort("Login Started")
    }

    override fun onSuccess(message: String) {
        toastShort(message)
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }

}
