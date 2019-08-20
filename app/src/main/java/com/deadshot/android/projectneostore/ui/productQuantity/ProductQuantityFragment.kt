package com.deadshot.android.projectneostore.ui.productQuantity


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentProductQuantityBinding
import com.deadshot.android.projectneostore.models.ProductDetail
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.ACCESS_TOKEN
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import com.deadshot.android.projectneostore.utils.toastShort
import com.google.gson.internal.bind.ArrayTypeAdapter
import timber.log.Timber

private const val PRODUCT_DETAIL = "productDetail"

class ProductQuantityFragment : DialogFragment(), AuthListener {

    private lateinit var productQuantityViewModel: ProductQuantityViewModel
    private lateinit var productQuantityModelFactory: ProductQuantityModelFactory
    private lateinit var access_token: String

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

        loadData()

        //Get product details from ProductDetail fragment
        val productDetail = arguments?.getParcelable<ProductDetail>(PRODUCT_DETAIL)

        productQuantityModelFactory = ProductQuantityModelFactory(access_token = access_token, productDetail = productDetail!!)
        productQuantityViewModel = ViewModelProviders.of(this, productQuantityModelFactory).get(ProductQuantityViewModel::class.java)

//        productQuantityViewModel.authListener.value = this

        binding.productQuantityViewModel = productQuantityViewModel

        val spinnerValues = arrayOf("1", "2", "3", "more")
        binding.spinnerQuantity.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, spinnerValues)
        binding.spinnerQuantity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                /**
                 * Default value of item as 1
                 */
                productQuantityViewModel.setQuantity(spinnerValues[1])
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                /**
                 * if item selected is not "more", then set the quantity
                 */
                if (spinnerValues[position] != "more")
                    productQuantityViewModel.setQuantity(spinnerValues[position])
                else
                    productQuantityViewModel.setQuantity(spinnerValues[1])

            }

        }

        productQuantityViewModel.authListener.value = this

        return binding.root
    }

    /**
     * Load data from shared preferences
     */
    fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        access_token = sharedPreferences.getString(ACCESS_TOKEN, getString(R.string.default_value))!!
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
