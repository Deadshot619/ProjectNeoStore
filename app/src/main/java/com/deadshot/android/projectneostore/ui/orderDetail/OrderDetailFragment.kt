package com.deadshot.android.projectneostore.ui.orderDetail


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.adapter.OrderDetailAdapter
import com.deadshot.android.projectneostore.databinding.FragmentOrderDetailBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.ui.BaseFragment
import com.deadshot.android.projectneostore.utils.ACCESS_TOKEN
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import com.deadshot.android.projectneostore.utils.toastShort
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class OrderDetailFragment : BaseFragment() {

    private lateinit var orderDetailViewModel: OrderDetailViewModel
    private lateinit var orderDetailModelFactory: OrderDetailModelFactory

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
//        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        val binding = FragmentOrderDetailBinding.inflate(inflater)

        //load data from shared preferences
        loadAccessToken()

        //set lifecyle owner
        binding.lifecycleOwner = this

        //get arguments from bundle passed from calling Fragment
        val arguments= OrderDetailFragmentArgs.fromBundle(arguments!!)

        orderDetailModelFactory = OrderDetailModelFactory(accessToken, arguments.orderId)
        orderDetailViewModel =  ViewModelProviders.of(this , orderDetailModelFactory).get(OrderDetailViewModel::class.java)

        binding.orderDetailViewModel = orderDetailViewModel

        // Adds divider to each recycler view item
        binding.rvOrderDetail.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.rvOrderDetail.adapter = OrderDetailAdapter()


        orderDetailViewModel.authListener.value = this
        return binding.root
    }
}
