package com.deadshot.android.projectneostore.ui.signUp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.deadshot.android.projectneostore.LoginFlowActivity

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentSignUpBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.toastShort
import timber.log.Timber

class SignUpFragment : Fragment(), AuthListener {
    private lateinit var signUpViewModel: SignUpViewModel

    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Adding Timber to fragment
        Timber.plant(Timber.DebugTree())

        /**
         * hide action bar
         */
//        (activity as LoginFlowActivity).supportActionBar?.hide()


        // Inflate the layout for this fragment
        binding=
            DataBindingUtil
                .inflate(inflater, R.layout.fragment_sign_up, container, false)

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        binding.lifecycleOwner = this

        binding.signUpViewModel = signUpViewModel

        signUpViewModel.authListener = this

        return binding.root
    }

    override fun onStarted() {
        toastShort("SignUp Started")
    }

    override fun onSuccess() {
        toastShort("SignUpSuccess")
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }
}
