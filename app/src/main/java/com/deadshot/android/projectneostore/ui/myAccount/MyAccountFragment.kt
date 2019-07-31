package com.deadshot.android.projectneostore.ui.myAccount


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.plant(Timber.DebugTree())
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false)
        loadData()

        //Setup View Model
        myAccountModelFactory = MyAccountModelFactory(firstName, lastName, email, phone, dob)
        myAccountViewModel = ViewModelProviders.of(this, myAccountModelFactory)
            .get(MyAccountViewModel::class.java)

        binding.lifecycleOwner = this
        binding.myAccountViewModel = myAccountViewModel

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
        dob = sharedPreferences.getString(DOB, "Not Available")
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
