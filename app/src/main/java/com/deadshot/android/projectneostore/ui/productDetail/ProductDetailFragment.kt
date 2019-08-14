package com.deadshot.android.projectneostore.ui.productDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.adapter.ProductDetailAdapter
import com.deadshot.android.projectneostore.databinding.FragmentProductDetailBinding
import com.deadshot.android.projectneostore.ui.rateProduct.RateProductFragment
import timber.log.Timber

private const val RATE_PRODUCT = "Rate Product"

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

        productDetailViewModel.rateButtonStatus.observe(this, Observer {
            it?.let {
                if (it){
                    val args = Bundle()
                    args.run {
                        putString("productName", productDetailViewModel.properties.value?.productName)
                        putString("productImage", productDetailViewModel.properties.value?.productImages?.get(0)?.imageUrl)
                        putInt("productRating", productDetailViewModel.properties.value?.rating!!)
//                        put
                    }
                    val dialog = RateProductFragment()
                    dialog.run {
                        arguments = args
                        show((activity as StoreFlowActivity).supportFragmentManager, RATE_PRODUCT)
                    }
                    productDetailViewModel.onClickRateButtonDone()
                }
            }
        })

        return binding.root
    }

}
