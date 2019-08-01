package com.deadshot.android.projectneostore.ui.myAccount


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
import com.deadshot.android.projectneostore.databinding.FragmentMyAccountBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.*
import timber.log.Timber

class MyAccountFragment : Fragment(), AuthListener {

    private lateinit var binding: FragmentMyAccountBinding
    private lateinit var myAccountViewModel: MyAccountViewModel
    private lateinit var myAccountModelFactory: MyAccountModelFactory

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var dob: String
    private lateinit var access_token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)

        //load data from shared preference
        loadData()

        //Setup View Model
        myAccountModelFactory = MyAccountModelFactory(firstName, lastName, email, phone, dob, access_token)
        myAccountViewModel = ViewModelProviders.of(this, myAccountModelFactory)
            .get(MyAccountViewModel::class.java)

        binding.lifecycleOwner = this
        binding.myAccountViewModel = myAccountViewModel

        myAccountViewModel.navigateEditProfileCheck.observe(this, Observer {
            if (it) {
                findNavController().navigate(MyAccountFragmentDirections.actionMyAccountFragmentToEditProfileFragment(
                    email, dob, firstName, lastName, phone, access_token
                ))
                myAccountViewModel.navigateDone()
            }
        })

        myAccountViewModel.authListener = this

        return binding.root
    }

    /**
     * Load data from shared preferences
     */
    fun loadData(){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        firstName = sharedPreferences.getString(FIRST_NAME, getString(R.string.default_value))
        lastName = sharedPreferences.getString(LAST_NAME, getString(R.string.default_value))
        email = sharedPreferences.getString(EMAIL, getString(R.string.default_value))
        phone = sharedPreferences.getString(PHONE_NUMBER, getString(R.string.default_value))
        dob = sharedPreferences.getString(DOB, getString(R.string.default_value))
        access_token = sharedPreferences.getString(ACCESS_TOKEN, getString(R.string.default_value))
    }

    override fun onStarted() {
        toastShort("SignUp Started")
    }

    override fun onSuccess(message: String) {
        toastShort("SignUpSuccess")
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }
}
