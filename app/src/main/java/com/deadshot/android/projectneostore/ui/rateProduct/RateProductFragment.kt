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
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.models.ProductImage
import timber.log.Timber

private const val PRODUCT_DETAIL = "productDetail"

class RateProductFragment : DialogFragment() {

    private lateinit var rateProductViewModel: RateProductViewModel
    private lateinit var rateProductModelFactory: RateProductModelFactory

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

        //Get product details from ProductDetail fragment
        val productDetail = arguments?.getParcelable<ProductDetail>(PRODUCT_DETAIL)

        rateProductModelFactory = RateProductModelFactory(productDetail = productDetail!!)
        rateProductViewModel = ViewModelProviders.of(this, rateProductModelFactory).get(RateProductViewModel::class.java)

        binding.rateProductViewModel = rateProductViewModel


        return binding.root
    }


}
