package com.deadshot.android.projectneostore.ui.productQuantity


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentProductQuantityBinding
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.toastShort
import timber.log.Timber

private const val PRODUCT_DETAIL = "productDetail"

class ProductQuantityFragment : DialogFragment(), AuthListener {

    private lateinit var productQuantityViewModel: ProductQuantityViewModel
    private lateinit var productQuantityModelFactory: ProductQuantityModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        val binding = FragmentProductQuantityBinding.inflate(inflater)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        //Get product details from ProductDetail fragment
        val productDetail = arguments?.getParcelable<ProductDetail>(PRODUCT_DETAIL)

        productQuantityModelFactory = ProductQuantityModelFactory(productDetail = productDetail!!)
        productQuantityViewModel = ViewModelProviders.of(this, productQuantityModelFactory).get(ProductQuantityViewModel::class.java)

//        productQuantityViewModel.authListener.value = this

        binding.productQuantityViewModel = productQuantityViewModel

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
