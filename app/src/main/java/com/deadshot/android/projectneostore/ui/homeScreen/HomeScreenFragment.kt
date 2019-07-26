package com.deadshot.android.projectneostore.ui.homeScreen


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentHomeScreenBinding
import timber.log.Timber

class HomeScreenFragment : Fragment() {

    private lateinit var binding : FragmentHomeScreenBinding
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Adding Timber to fragment
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        binding.lifecycleOwner = this


        return binding.root
    }
}
