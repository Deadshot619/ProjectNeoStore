package com.deadshot.android.projectneostore.ui.signUp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentSignUpBinding
import com.deadshot.android.projectneostore.ui.BaseAuthListener

class SignUpFragment : BaseAuthListener(){
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Adding Timber to fragment
//        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        binding=
            DataBindingUtil
                .inflate(inflater, R.layout.fragment_sign_up, container, false)

        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        binding.lifecycleOwner = this

        binding.signUpViewModel = signUpViewModel

        signUpViewModel.authListener.value = this

        return binding.root
    }
}
