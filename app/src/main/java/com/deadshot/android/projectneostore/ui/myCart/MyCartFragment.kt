package com.deadshot.android.projectneostore.ui.myCart


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.contains
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.adapter.MyCartAdapter
import com.deadshot.android.projectneostore.databinding.FragmentMyCartBinding
import com.deadshot.android.projectneostore.models.ProductsInfo
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber

class MyCartFragment : Fragment(), AuthListener {

    private lateinit var myCartViewModel: MyCartViewModel
    private lateinit var myCartModelFactory: MyCartModelFactory
    private lateinit var access_token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        val binding = FragmentMyCartBinding.inflate(inflater)

        //load data from shared preferences
        loadData()

        myCartModelFactory = MyCartModelFactory(access_token = access_token)
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

        myCartViewModel.authListener.value = this
        return binding.root
    }

        /**
         * Load data from shared preferences
         */
        private fun loadData(){
            val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
            access_token = sharedPreferences.getString(ACCESS_TOKEN, getString(R.string.default_value))!!
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
