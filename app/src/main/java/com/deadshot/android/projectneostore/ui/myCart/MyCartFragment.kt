package com.deadshot.android.projectneostore.ui.myCart


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.adapter.MyCartAdapter
import com.deadshot.android.projectneostore.databinding.FragmentMyCartBinding
import com.deadshot.android.projectneostore.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        myCartModelFactory = MyCartModelFactory(access_token = access_token)
        myCartViewModel = ViewModelProviders.of(this, myCartModelFactory).get(MyCartViewModel::class.java)

        //set lifecyle owner
        binding.lifecycleOwner = this

        binding.myCartViewModel = myCartViewModel

        // Adds divider to each recycler view item
        binding.rvMyCartItems.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        binding.rvMyCartItems.adapter = MyCartAdapter(MyCartAdapter.OnClickDeleteListener {
            val builder = MaterialAlertDialogBuilder(context!!)
                .setTitle(R.string.remove_item)
                .setMessage(R.string.remove_message)
                .setPositiveButton(R.string.action_remove){_,_ -> Toast.makeText(context, "Remove", Toast.LENGTH_LONG).show()}
                .setNegativeButton(R.string.action_cancel){_,_ -> Toast.makeText(context, "Cancel", Toast.LENGTH_LONG).show()}
                .create()
            builder.show()

        })

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
