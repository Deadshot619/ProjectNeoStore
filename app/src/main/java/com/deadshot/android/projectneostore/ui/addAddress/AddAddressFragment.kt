package com.deadshot.android.projectneostore.ui.addAddress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.database.AddressDatabase
import com.deadshot.android.projectneostore.databinding.FragmentAddAddressBinding

/**
 * A simple [Fragment] subclass.
 */
class AddAddressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddAddressBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application

        val database = AddressDatabase.getInstance(application).addressDatabaseDao

        val addAddressModelFactory = AddAddressModelFactory(database, application)

        val addAddressViewModel =
            ViewModelProviders.of(this, addAddressModelFactory).get(AddAddressViewModel::class.java)

        binding.lifecycleOwner = this

        binding.addAddressViewModel = addAddressViewModel

        return binding.root
    }


}
