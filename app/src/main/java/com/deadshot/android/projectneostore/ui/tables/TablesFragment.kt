package com.deadshot.android.projectneostore.ui.tables


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentTablesBinding
import timber.log.Timber

class TablesFragment : Fragment() {

    private lateinit var binding: FragmentTablesBinding
    private lateinit var tablesViewModel: TablesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Add Timber
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tables, container, false)

        //Add Lifecycle owner to this fragment
        binding.lifecycleOwner = this



        return binding.root
    }
}
