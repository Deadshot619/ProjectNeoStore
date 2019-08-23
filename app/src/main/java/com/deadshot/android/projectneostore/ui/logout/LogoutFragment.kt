package com.deadshot.android.projectneostore.ui.logout


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.databinding.FragmentLogoutBinding
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LogoutFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLogoutBinding.inflate(inflater)

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(LogoutFragmentDirections.actionLogoutFragmentToLoginFlowActivity())
            clearSharedPreferences()
            (activity as StoreFlowActivity).finish()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }


        return binding.root
    }

    private fun clearSharedPreferences() {
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE) ?: return
        sharedPreferences.edit().clear().apply()
    }

}
