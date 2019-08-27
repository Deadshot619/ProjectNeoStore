package com.deadshot.android.projectneostore.ui.myOrders


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadshot.android.projectneostore.adapter.OrderListAdapter
import com.deadshot.android.projectneostore.databinding.FragmentMyOrdersBinding
import com.deadshot.android.projectneostore.ui.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class MyOrdersFragment : BaseFragment() {

    private val myOrdersViewModel by lazy {
        ViewModelProviders.of(this, myOrdersModelFactory).get(MyOrdersViewModel::class.java)
    }
    private val myOrdersModelFactory by lazy {
        MyOrdersModelFactory(accessToken = accessToken)
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
//        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        val binding =  FragmentMyOrdersBinding.inflate(inflater)

        //load data from shared preferences
        loadAccessToken()

        //set lifecyle owner
        binding.lifecycleOwner = this
        binding.myOrdersViewModel = myOrdersViewModel

        // Adds divider to each recycler view item
        binding.rvMyOrderList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.rvMyOrderList.adapter = OrderListAdapter(OrderListAdapter.OnClickListener {
//            Toast.makeText(context, it.price.toString(), Toast.LENGTH_LONG).show()
            myOrdersViewModel.onOrderItemClicked(it.id)
        })

        myOrdersViewModel.navigateToOrderDetail.observe(this, Observer {
            orderId->
                orderId?.let {
                    this.findNavController().navigate(MyOrdersFragmentDirections.actionMyOrdersFragmentToOrderDetailFragment(orderId))
                    myOrdersViewModel.onOrderItemNavigated()
            }
        })

        myOrdersViewModel.authListener.value = this
        return binding.root
    }
}
