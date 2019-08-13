package com.deadshot.android.projectneostore.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.deadshot.android.projectneostore.adapter.ProductDetailAdapter
import com.deadshot.android.projectneostore.databinding.FragmentProductDetailBinding
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
                binding.propertyImage = it.productImages[0]
            }
        })

        binding.rvProductImages.adapter = ProductDetailAdapter(ProductDetailAdapter.OnClickListener {
            binding.propertyImage = it
        })

        return binding.root
    }

}
