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
private const val PRODUCT_DETAIL = "productDetail"

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
                binding.propertyImage = it.productImages?.get(0)
            }
        })

        /**
         * Set Recycler View
         */
        binding.rvProductImages.adapter = ProductDetailAdapter(ProductDetailAdapter.OnClickListener {
            binding.propertyImage = it
        })

        /**
         * Checks if the Rate button is pressed
         */
        val dialog by lazy {
            RateProductFragment()
        }
        productDetailViewModel.rateButtonStatus.observe(this, Observer {
            it?.let {
                if (it){
                    /**
                     * Make a parcelable bundle of type [ProductDetail]
                     */
                    val args = Bundle()
                    args.putParcelable(PRODUCT_DETAIL, productDetailViewModel.properties.value)

                    /**
                     * Create a Dialog for Rating
                     */
                    dialog.arguments = args
                    dialog.show((activity as StoreFlowActivity).supportFragmentManager, RATE_PRODUCT)
                    productDetailViewModel.onClickRateButtonDone()
                }
            }
        })


        return binding.root
    }

}
