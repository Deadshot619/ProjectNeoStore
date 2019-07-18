package com.deadshot.android.projectneostore.ui.Login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil
                .inflate(inflater, R.layout.fragment_login, container, false)

        //get ViewModel
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.tvLoginPageSignUp.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment2_to_signUpFragment2)
        )

        return binding.root
    }


}
