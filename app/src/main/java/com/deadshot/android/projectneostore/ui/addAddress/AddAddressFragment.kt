package com.deadshot.android.projectneostore.ui.addAddress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.deadshot.android.projectneostore.R
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


        return binding.root
    }


}
