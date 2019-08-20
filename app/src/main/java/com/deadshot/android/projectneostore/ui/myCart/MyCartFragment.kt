package com.deadshot.android.projectneostore.ui.myCart


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentMyCartBinding
import com.deadshot.android.projectneostore.utils.*
import timber.log.Timber

class MyCartFragment : Fragment() {

    private lateinit var myCartViewModel: MyCartViewModel
    private lateinit var myCartModelFactory: MyCartModelFactory
    private lateinit var access_token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        val binding = FragmentMyCartBinding.inflate(inflater)

        //load data from shared preferences
        loadData()

        myCartViewModel = ViewModelProviders.of(this).get(MyCartViewModel::class.java)

        //set lifecyle owner
        binding.lifecycleOwner = this

        binding.myCartViewModel = myCartViewModel


        return binding.root
    }

    /**
     * Load data from shared preferences
     */
    fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        access_token = sharedPreferences.getString(ACCESS_TOKEN, getString(R.string.default_value))!!
    }

}
