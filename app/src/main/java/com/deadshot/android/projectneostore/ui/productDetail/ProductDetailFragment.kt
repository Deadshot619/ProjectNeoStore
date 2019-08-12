package com.deadshot.android.projectneostore.ui.productDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.navArgs

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.databinding.FragmentProductDetailBinding
import com.deadshot.android.projectneostore.ui.products.ProductsModelFactory
import com.deadshot.android.projectneostore.ui.products.ProductsViewModel
import timber.log.Timber

class ProductDetailFragment : Fragment() {

    /**
     * Lazily initialize our [ProductDetailViewModel] & [ProductsModelFactory].
     */
    private val productDetailViewModel: ProductDetailViewModel by lazy{
        ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        val binding = FragmentProductDetailBinding.inflate(inflater)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ProductsViewModel

        val productId = ProductDetailFragmentArgs.fromBundle(arguments!!).productId
        val productName = ProductDetailFragmentArgs.fromBundle(arguments!!).productName
        binding.demoText.text = productId.toString()
        
        return binding.root
    }

}
