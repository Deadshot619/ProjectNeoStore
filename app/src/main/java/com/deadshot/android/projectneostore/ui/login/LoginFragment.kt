package com.deadshot.android.projectneostore.ui.login


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.deadshot.android.projectneostore.LoginFlowActivity
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentLoginBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.ui.BaseFragment
import com.deadshot.android.projectneostore.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber


class LoginFragment : BaseFragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Adding Timber to fragment
        Timber.plant(Timber.DebugTree())

        /**
         * hide action bar
         */
        (activity as LoginFlowActivity).supportActionBar?.hide()

        //Load data from Shared Preference
        loadAuthData()

        // Inflate the layout for this fragment
        binding =
            DataBindingUtil
                .inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this

        //get ViewModel
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel

        /**
         * Skip login if email & password is empty
         */
        if (email != null){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragment2ToStoreFlowActivity())
            loginViewModel.loginDone()
            //Manually popping off Login Flow Activity
            (activity as LoginFlowActivity).finish()
        }


        /**
         * authListener for showing Toast from Viewmodel/Repo
         */
        loginViewModel.authListener.value = this

        binding.tvLoginPageSignUp.setOnClickListener (
            Navigation.createNavigateOnClickListener(LoginFragmentDirections.actionLoginFragment2ToSignUpFragment2())
        )

        loginViewModel.loginCheck.observe(this, Observer {
            if (it){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragment2ToStoreFlowActivity())
                loginViewModel.loginDone()
                //Manually popping off Login Flow Activity
                (activity as LoginFlowActivity).finish()
            }
        })

        /**
         * Lazily create a Progress Dialog
         */
        val progressDialog by lazy{
            MaterialAlertDialogBuilder(context)
                .setView(R.layout.layout_loading)
                .setCancelable(false)
                .create()
        }

        loginViewModel.progressBarStatus.observe(this, Observer {
            if (it){
//                requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                //Show Progress Dialog
                progressDialog.show()
            }else{
//                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                //Hide Progress Dialog
                progressDialog.dismiss()
            }
        })

        loginViewModel.userData.observe(this, Observer {
            if (it != null){
                saveAuthData(
                    firstName = it.first_name,
                    lastName = it.last_name,
                    email = it.email,
                    phone = it.phone_no.toString(),
                    accessToken = it.access_token,
                    dob = (it.dob ?: getString(R.string.default_value)).toString()
                )
            }
        })

        return binding.root
    }

    override fun onStarted() {
        toastShort("Login Started")
    }

    override fun onSuccess(message: String) {
        toastShort("Login Success")
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }
}
