package com.deadshot.android.projectneostore.ui.resetPassword


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentResetPasswordBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.ACCESS_TOKEN
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import com.deadshot.android.projectneostore.utils.toastShort
import timber.log.Timber

class ResetPasswordFragment : Fragment(), AuthListener {

    private lateinit var binding: FragmentResetPasswordBinding

    /**
     * Set viewModel Factory
     */
    private val resetPasswordViewModel by lazy {
        ViewModelProviders.of(this, resetPasswordModelFactory).get(ResetPasswordViewModel::class.java)
    }
    private val resetPasswordModelFactory by lazy {
        ResetPasswordModelFactory(access_token = access_token)
    }

    private lateinit var access_token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false)
        binding.lifecycleOwner = this
        loadData()
        //Set ViewModel
        binding.resetPasswordViewModel = resetPasswordViewModel

        resetPasswordViewModel.checkResetPasswordSuccessful.observe(this, Observer {
            if (it){
                findNavController().navigate(ResetPasswordFragmentDirections.actionResetPasswordFragmentToMyAccountFragment())
                resetPasswordViewModel.resetDone()
            }
        })

        resetPasswordViewModel.authListener.value = this
        return binding.root
    }


    /**
     * Load data from shared preferences
     */
    fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        access_token = sharedPreferences.getString(ACCESS_TOKEN, getString(R.string.default_value))
    }
    override fun onStarted() {
    }

    override fun onSuccess(message: String) {
        toastShort(message)
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }
}
