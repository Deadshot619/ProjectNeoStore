package com.deadshot.android.projectneostore.ui.myAccount


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.FragmentMyAccountBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.toEditable
import com.deadshot.android.projectneostore.utils.toastShort
import timber.log.Timber

class MyAccountFragment : Fragment(), AuthListener {

    private lateinit var myAccountViewModel: MyAccountViewModel
    private lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)

        myAccountViewModel = ViewModelProviders.of(this).get(MyAccountViewModel::class.java)
        binding.lifecycleOwner = this
        binding.myAccountViewModel = myAccountViewModel


        myAccountViewModel.phoneNumber.observe(this, Observer {
            binding.run {
                etFirstNameInputEditLayout.text = myAccountViewModel!!.firstName.value?.toEditable()
                etLastNameInputEditLayout.text = myAccountViewModel!!.lastName.value?.toEditable()
                etEmailIdInputTextLayout.text = myAccountViewModel!!.emailId.value?.toEditable()
                etPhoneInputEditLayout.text = myAccountViewModel!!.phoneNumber.value?.toEditable()
            }
        })


        myAccountViewModel.authListener = this
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
