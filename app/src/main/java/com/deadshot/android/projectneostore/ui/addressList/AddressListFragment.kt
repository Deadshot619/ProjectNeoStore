package com.deadshot.android.projectneostore.ui.addressList


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.database.AddressDatabase
import com.deadshot.android.projectneostore.databinding.FragmentAddressListBinding

/**
 * A simple [Fragment] subclass.
 */
class AddressListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddressListBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val database = AddressDatabase.getInstance(application).addressDatabaseDao

        val addressListModelFactory = AddressListModelFactory(database, application)
        val addressListViewModel = ViewModelProviders.of(this, addressListModelFactory).get(AddressListViewModel::class.java)

        binding.addressListViewModel = addressListViewModel

        binding.lifecycleOwner = this

        addressListViewModel.navigateToAddAddress.observe(this, Observer {
            it?.let {
                if (it){
                    findNavController().navigate(AddressListFragmentDirections.actionAddressListFragmentToAddAddressFragment())
                    addressListViewModel.navigateToAddAddressDone()
                }
            }
        })



        return binding.root
    }
}
