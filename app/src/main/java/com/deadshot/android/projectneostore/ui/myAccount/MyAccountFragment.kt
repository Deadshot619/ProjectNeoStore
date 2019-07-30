package com.deadshot.android.projectneostore.ui.myAccount


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentMyAccountBinding

class MyAccountFragment : Fragment() {

    private lateinit var myAccountViewModel: MyAccountViewModel
    private lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)


        return binding.root
    }
}
