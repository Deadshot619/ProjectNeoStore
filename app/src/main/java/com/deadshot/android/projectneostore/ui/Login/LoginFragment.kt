package com.deadshot.android.projectneostore.ui.Login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentLoginBinding
import com.deadshot.android.projectneostore.models.User
import com.deadshot.android.projectneostore.services.DestinationLoginService
import com.deadshot.android.projectneostore.services.ServiceBuilder
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.toastShort
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment(), AuthListener {

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
        viewModel.authListener = this

        binding.tvLoginPageSignUp.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment2_to_signUpFragment2)
        )

        binding.btnLogin.setOnClickListener {


            val email = binding.usernameInputEditLayout.text.toString()
            val password = binding.passwordInputEditLayout.text.toString()

            val destinationService = ServiceBuilder.buildService(DestinationLoginService::class.java)
            val requestCall = destinationService.checkLogin(email, password)

            requestCall.enqueue(object: Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(context,"Login Failed", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        Toast.makeText(context,"Login Successful", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,"Login UnSuccessful", Toast.LENGTH_LONG).show()
                    }
                }

            })


        }

        return binding.root
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
