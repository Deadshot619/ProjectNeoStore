package com.deadshot.android.projectneostore.ui.editProfile


import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.databinding.FragmentEditProfileBinding
import com.deadshot.android.projectneostore.ui.AuthListener
import com.deadshot.android.projectneostore.utils.*
import timber.log.Timber
import java.util.*


class EditProfileFragment : Fragment(), AuthListener {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel
    private lateinit var editProfileModelFactory: EditProfileModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Add Timber to fragment
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        binding.lifecycleOwner = this

        val args = EditProfileFragmentArgs.fromBundle(arguments!!)
//        Timber.i("${args.dob} ${args.email} ${args.firstName}")

        editProfileModelFactory = EditProfileModelFactory(
            args.firstName,
            args.lastName,
            args.email,
            args.phone,
            args.dob,
            args.accessToken
        )
        editProfileViewModel = ViewModelProviders.of(this, editProfileModelFactory).get(EditProfileViewModel::class.java)
        binding.editProfileViewModel = editProfileViewModel


        /**
         * Date Picker
         */
        binding.etDobInputLayout.setEndIconOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(   activity as StoreFlowActivity, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                editProfileViewModel.dob = "$mDay-${mMonth+1}-$mYear"
                binding.invalidateAll()
            },year, month, day)

            datePickerDialog.show()
        }

        editProfileViewModel.checkUpdateSuccessful.observe(this, androidx.lifecycle.Observer {
            if (it){
                saveData(
                    firstName = editProfileViewModel.first_name,
                    lastName = editProfileViewModel.last_name,
                    email = editProfileViewModel.email_id,
                    phone = editProfileViewModel.phone_number,
                    dob = editProfileViewModel.dob
                )
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToMyAccountFragment())
                editProfileViewModel.updateDone()
            }
        })

        editProfileViewModel.authListener = this
        return binding.root
    }

    /**
     *  Save data in Shared Preferences
     */
    fun saveData(firstName: String, lastName: String, email: String, phone: String, dob: String){
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        with(sharedPreferences.edit()){
            putString(FIRST_NAME, firstName)
            putString(LAST_NAME, lastName)
            putString(EMAIL, email)
            putString(PHONE_NUMBER, phone)
            putString(DOB, dob)
            apply()
        }
    }

    override fun onStarted() {
        toastShort("SignUp Started")
    }

    override fun onSuccess(message: String) {
        toastShort(message)
    }

    override fun onFailure(message: String) {
        toastShort(message)
    }
}
