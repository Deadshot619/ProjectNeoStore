package com.deadshot.android.projectneostore.ui.homeScreen


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.deadshot.android.projectneostore.LoginFlowActivity

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentHomeScreenBinding
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import timber.log.Timber

class HomeScreenFragment : Fragment() {

    private lateinit var binding : FragmentHomeScreenBinding
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Add Timber to fragment
        Timber.plant(Timber.DebugTree())
        /**
         * show action bar
         */
        (activity as LoginFlowActivity).supportActionBar?.show()


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        /**
         *  Setup auto image SliderView
         */
        val sliderView = binding.imageSlider
        val adapter = HomeScreenSliderAdapter()
        sliderView.run {
            sliderAdapter = adapter
            setIndicatorAnimation(IndicatorAnimations.WORM)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }
}
