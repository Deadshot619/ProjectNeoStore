package com.deadshot.android.projectneostore.ui.homeScreen

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.databinding.FragmentHomeScreenBinding
import com.deadshot.android.projectneostore.utils.*
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.activity_store_flow.*
import kotlinx.android.synthetic.main.nav_header.view.*
import timber.log.Timber

class HomeScreenFragment : Fragment() {

    private lateinit var binding : FragmentHomeScreenBinding
    private lateinit var homeScreenViewModel: HomeScreenViewModel

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Add Timber to fragment
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_screen, container, false)
        binding.lifecycleOwner = this

        loadData()

        homeScreenViewModel = ViewModelProviders.of(this).get(HomeScreenViewModel::class.java)
        binding.homeScreenViewModel = homeScreenViewModel

        setHasOptionsMenu(true)

        /**
         * Add Name & email to NavHeader
         */
        with((activity as StoreFlowActivity).navView.getHeaderView(0)){
            tv_userRealName.text = "${firstName.capitalize()} ${lastName.capitalize()}"
            tv_userEmail.text = email
            invalidate()
        }

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

        //Navigate to Tables Screen
        homeScreenViewModel.navigateToTable.observe(this, Observer {
            if (it == true){
                findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToTablesFragment())
                homeScreenViewModel.navigateToTablesDone()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) ||
                super.onOptionsItemSelected(item)
    }

    fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        firstName = sharedPreferences.getString(FIRST_NAME, getString(R.string.default_value))
        lastName = sharedPreferences.getString(LAST_NAME, getString(R.string.default_value))
        email = sharedPreferences.getString(EMAIL, getString(R.string.default_value))
    }
}
