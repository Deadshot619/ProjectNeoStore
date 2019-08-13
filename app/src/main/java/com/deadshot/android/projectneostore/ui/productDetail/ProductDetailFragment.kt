package com.deadshot.android.projectneostore.ui.productDetail

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.adapter.ProductDetailAdapter
import com.deadshot.android.projectneostore.databinding.FragmentProductDetailBinding
import com.deadshot.android.projectneostore.ui.products.ProductsModelFactory
import com.deadshot.android.projectneostore.ui.products.ProductsViewModel
import timber.log.Timber

class ProductDetailFragment : Fragment() {

    private lateinit var productDetailViewModel: ProductDetailViewModel
    private lateinit var productDetailModelFactory: ProductDetailModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
        Timber.plant(Timber.DebugTree())
        val application = requireNotNull(activity).application
        // Inflate the layout for this fragment
        val binding = FragmentProductDetailBinding.inflate(inflater)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        val args = ProductDetailFragmentArgs.fromBundle(arguments!!)
        productDetailModelFactory = ProductDetailModelFactory(productId = args.productId, app = application)
        productDetailViewModel =
            ViewModelProviders.of(this, productDetailModelFactory).get(ProductDetailViewModel::class.java)

        binding.productDetailViewModel = productDetailViewModel

        productDetailViewModel.properties.observe(this, Observer {
            it?.let {
                binding.property = it
            }
        })

        binding.rvProductImages.adapter = ProductDetailAdapter()

        return binding.root
    }

}
