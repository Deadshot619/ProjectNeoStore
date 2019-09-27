package com.deadshot.android.projectneostore.ui.addressList


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.adapter.AddressListAdapter
import com.deadshot.android.projectneostore.database.AddressDatabase
import com.deadshot.android.projectneostore.databinding.FragmentAddressListBinding
import com.deadshot.android.projectneostore.ui.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass.
 */
class AddressListFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddressListBinding.inflate(inflater)

        loadAccessToken()

        val application = requireNotNull(this.activity).application

        val database = AddressDatabase.getInstance(application).addressDatabaseDao

        val addressListModelFactory = AddressListModelFactory(accessToken, database, application)
        val addressListViewModel = ViewModelProviders.of(this, addressListModelFactory).get(AddressListViewModel::class.java)

        binding.addressListViewModel = addressListViewModel

        binding.lifecycleOwner = this

        val adapter = AddressListAdapter(AddressListAdapter.OnClickListener {
            /**
             * Lazily build a Alert Dialog
             */
            val builder by lazy {
                MaterialAlertDialogBuilder(context!!)
                    .setTitle(R.string.remove_item)
                    .setMessage(R.string.remove_message)
                    .setPositiveButton(R.string.action_remove){_,_ ->
                        addressListViewModel.deleteAddress(it)
                        Toast.makeText(context, "Removed!", Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton(R.string.action_cancel){_,_ -> Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show()}
                    .create()
            }
            builder.show()
        })
        // Adds divider to each recycler view item
        binding.rvAddressList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.rvAddressList.adapter = adapter


        addressListViewModel.navigateToAddAddress.observe(this, Observer {
            it?.let {
                if (it){
                    findNavController().navigate(AddressListFragmentDirections.actionAddressListFragmentToAddAddressFragment())
                    addressListViewModel.navigateToAddAddressDone()
                }
            }
        })

        addressListViewModel.addresses.observe(this, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}
