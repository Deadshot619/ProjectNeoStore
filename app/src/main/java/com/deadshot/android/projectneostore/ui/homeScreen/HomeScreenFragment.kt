package com.deadshot.android.projectneostore.ui.homeScreen


import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.deadshot.android.projectneostore.LoginFlowActivity

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentHomeScreenBinding
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter
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
        /**
         * show action bar
         */
        (activity as LoginFlowActivity).supportActionBar?.show()


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        /**
         *
         */
        val sliderView = binding.imageSlider
        val adapter = HomeScreenSliderAdapter()
        sliderView.sliderAdapter = adapter
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM)//set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }
}
