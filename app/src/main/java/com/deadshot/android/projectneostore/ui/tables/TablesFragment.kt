package com.deadshot.android.projectneostore.ui.tables


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.deadshot.android.projectneostore.Adapter.ProductsAdapter

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentTablesBinding
import com.deadshot.android.projectneostore.databinding.LayoutProductBinding
import timber.log.Timber

class TablesFragment : Fragment() {

    private lateinit var tablesViewModel: TablesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        val binding = FragmentTablesBinding.inflate(inflater)
        tablesViewModel = ViewModelProviders.of(this).get(TablesViewModel::class.java)
        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this

        binding.tablesViewModel = tablesViewModel

        binding.recyclerViewProducts.adapter = ProductsAdapter()

        return binding.root
    }
}
