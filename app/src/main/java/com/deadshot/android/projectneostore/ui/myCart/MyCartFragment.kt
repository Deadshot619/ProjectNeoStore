package com.deadshot.android.projectneostore.ui.myCart


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.adapter.MyCartAdapter
import com.deadshot.android.projectneostore.databinding.FragmentMyCartBinding
import com.deadshot.android.projectneostore.models.ProductsInfo
import com.deadshot.android.projectneostore.ui.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber

class MyCartFragment : BaseFragment(){

    private lateinit var myCartViewModel: MyCartViewModel
    private lateinit var myCartModelFactory: MyCartModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        val binding = FragmentMyCartBinding.inflate(inflater)

        //load data from shared preferences
        loadAccessToken()

        myCartModelFactory = MyCartModelFactory(access_token = accessToken)
        myCartViewModel = ViewModelProviders.of(this, myCartModelFactory).get(MyCartViewModel::class.java)

        //set lifecyle owner
        binding.lifecycleOwner = this

        binding.myCartViewModel = myCartViewModel


        // Adds divider to each recycler view item
        binding.rvMyCartItems.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.rvMyCartItems.adapter = MyCartAdapter(MyCartAdapter.OnClickDeleteListener {
            /**
             * Lazily build a Alert Dialog
             */
            val builder by lazy {
                MaterialAlertDialogBuilder(context!!)
                    .setTitle(R.string.remove_item)
                    .setMessage(R.string.remove_message)
                    .setPositiveButton(R.string.action_remove){_,_ -> myCartViewModel.deleteFromCart(it)}
                    .setNegativeButton(R.string.action_cancel){_,_ -> Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show()}
                    .create()
            }
            builder.show()
        },
            object : MyCartAdapter.OnSelectedItemListener{
                override fun onItemSelected(productsInfo: ProductsInfo, position: Int) {
                    myCartViewModel.editCart(productsInfo.productId, position+1)
                    Timber.i("OnItemClickListener : $position")
                }
            })

        myCartViewModel.reloadCartStatus.observe(this, Observer {
            /**
             * Loads the cart when reload cart status is true
             */
            it?.let {
                if (it)
                    myCartViewModel.loadCart()
            }
        })

        /**
         * Navigate to AddressList
         */
        myCartViewModel.navigateToAddressStatus.observe(this, Observer {
            it?.let {
                if (it){
                    findNavController().navigate(MyCartFragmentDirections.actionMyCartFragmentToAddressListFragment())
                    myCartViewModel.navigateToAddressDone()
                }
            }
        })

        myCartViewModel.authListener.value = this
        return binding.root
    }
}
