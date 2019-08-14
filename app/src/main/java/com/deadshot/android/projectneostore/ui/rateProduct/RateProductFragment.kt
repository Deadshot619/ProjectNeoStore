package com.deadshot.android.projectneostore.ui.rateProduct


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentRateProductBinding
import timber.log.Timber

class RateProductFragment : DialogFragment() {

    private lateinit var rateProductViewModel: RateProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        val binding = FragmentRateProductBinding.inflate(inflater)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        rateProductViewModel = ViewModelProviders.of(this).get(RateProductViewModel::class.java)

        binding.rateProductViewModel = rateProductViewModel

        binding.tvProductName.text = arguments!!.getString("productName")

        return binding.root
    }


}
