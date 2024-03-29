package com.deadshot.android.projectneostore.ui.products


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadshot.android.projectneostore.adapter.ProductsAdapter
import com.deadshot.android.projectneostore.databinding.FragmentProductsListBinding
import com.deadshot.android.projectneostore.utils.TABLES

class TablesFragment : Fragment() {

    /**
     * Lazily initialize our [ProductsViewModel].
     */
    private val productsViewModel: ProductsViewModel by lazy {
        ViewModelProviders.of(this, productsModelFactory).get(ProductsViewModel::class.java)
    }
    private val productsModelFactory by lazy {
        ProductsModelFactory(TABLES)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
//        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        val binding = FragmentProductsListBinding.inflate(inflater)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ProductsViewModel
        binding.productsViewModel = productsViewModel

        // Adds divider to each recycler view item
        binding.recyclerViewProducts.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        // Sets the adapter of the products RecyclerView
        binding.recyclerViewProducts.adapter = ProductsAdapter(ProductsAdapter.OnClickListener {
            findNavController().navigate(ChairsFragmentDirections.actionSofasFragmentToProductDetailFragment(it.productId, it.productName))
        })

        return binding.root
    }
}
