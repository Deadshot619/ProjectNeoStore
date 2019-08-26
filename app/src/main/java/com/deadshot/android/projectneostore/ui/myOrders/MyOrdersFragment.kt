package com.deadshot.android.projectneostore.ui.myOrders


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentMyOrdersBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.ACCESS_TOKEN
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import com.deadshot.android.projectneostore.utils.toastShort

/**
 * A simple [Fragment] subclass.
 */
class MyOrdersFragment : Fragment(), AuthListener {

    private val myOrdersViewModel by lazy {
        ViewModelProviders.of(this, myOrdersModelFactory).get(MyOrdersViewModel::class.java)
    }
    private val myOrdersModelFactory by lazy {
        MyOrdersModelFactory(accessToken = access_token)
    }

    private lateinit var access_token: String

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentMyOrdersBinding.inflate(inflater)

        //load data from shared preferences
        loadData()

        //set lifecyle owner
        binding.lifecycleOwner = this


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
