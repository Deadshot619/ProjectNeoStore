package com.deadshot.android.projectneostore.ui.rateProduct


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentRateProductBinding
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.models.ProductImage
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.toastShort
import timber.log.Timber

private const val PRODUCT_DETAIL = "productDetail"

class RateProductFragment : DialogFragment(), AuthListener {

    private lateinit var rateProductViewModel: RateProductViewModel
    private lateinit var rateProductModelFactory: RateProductModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
//        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        val binding = FragmentRateProductBinding.inflate(inflater)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        //Get product details from ProductDetail fragment
        val productDetail = arguments?.getParcelable<ProductDetail>(PRODUCT_DETAIL)

        rateProductModelFactory = RateProductModelFactory(productDetail = productDetail!!)
        rateProductViewModel = ViewModelProviders.of(this, rateProductModelFactory).get(RateProductViewModel::class.java)

        rateProductViewModel.authListener.value = this

        binding.rateProductViewModel = rateProductViewModel

        /**
         * if product is rated successfully, then dismiss the fragment
         */
        rateProductViewModel.status.observe(this, Observer {
            it?.let {
                if (it)
                    dismiss()
            }
        })

        return binding.root
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
