package com.deadshot.android.projectneostore.ui.login


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.deadshot.android.projectneostore.LoginFlowActivity
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentLoginBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.*
import timber.log.Timber


class LoginFragment : Fragment(), AuthListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding





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

        // Inflate the layout for this fragment
        binding =
            DataBindingUtil
                .inflate(inflater, R.layout.fragment_login, container, false)

        binding.lifecycleOwner = this

        //get ViewModel
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel

        loginViewModel.authListener = this

        binding.tvLoginPageSignUp.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment2_to_signUpFragment2)
        )

        loginViewModel.loginCheck.observe(this, Observer {
            if (it){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragment2ToStoreFlowActivity())
                loginViewModel.loginDone()
                //Manually popping off Login Flow Activity
                (activity as LoginFlowActivity).finish()
            }
        })

        loginViewModel.userData.observe(this, Observer {
            if (it != null){
                saveData(firstName = it.first_name,
                    lastName = it.last_name,
                    email = it.email,
                    phone = it.phone_no.toString(),
                    accessToken = it.access_token,
                    dob = (it.dob ?: getString(R.string.default_value)).toString()
                )
            }
        })

//        activity?.actionBar?.hide()
        return binding.root
    }

    /**
     *  Save data in Shared Preferences
     */
    fun saveData(firstName: String, lastName: String, email: String, phone: String, accessToken: String, dob: String){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()){
            putString(FIRST_NAME, firstName)
            putString(LAST_NAME, lastName)
            putString(EMAIL, email)
            putString(PHONE_NUMBER, phone)
            putString(ACCESS_TOKEN, accessToken)
            putString(DOB, dob)
            apply()
        }
    }

    override fun onStarted() {
        toastShort("Login Started")
    }

    override fun onSuccess() {
        toastShort("Login Success")
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }
}
