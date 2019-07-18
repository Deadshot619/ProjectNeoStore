package com.deadshot.android.projectneostore.ui.SignUp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=
            DataBindingUtil
                .inflate(inflater, R.layout.fragment_sign_up, container, false)

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        return binding.root
    }
}
