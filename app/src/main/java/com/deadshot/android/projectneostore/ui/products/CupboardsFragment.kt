package com.deadshot.android.projectneostore.ui.products


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deadshot.android.projectneostore.Adapter.ProductsAdapter

import com.deadshot.android.projectneostore.databinding.FragmentTablesBinding
import timber.log.Timber

class CupboardsFragment : Fragment() {

    /**
     * Lazily initialize our [ProductsViewModel].
     */
    private val productsViewModel: ProductsViewModel by lazy {
        ViewModelProviders.of(this).get(ProductsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        val binding = FragmentTablesBinding.inflate(inflater)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ProductsViewModel
        binding.productsViewModel = productsViewModel

        // Adds divider to each recycler view item
        binding.recyclerViewProducts.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        // Sets the adapter of the products RecyclerView
        binding.recyclerViewProducts.adapter = ProductsAdapter()

        return binding.root
    }
}
