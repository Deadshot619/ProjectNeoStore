package com.deadshot.android.projectneostore.ui.editProfile


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.databinding.FragmentEditProfileBinding
import com.deadshot.android.projectneostore.ui.BaseFragment
import java.util.*


class EditProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel
    private lateinit var editProfileModelFactory: EditProfileModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Add Timber to fragment
//        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        binding.lifecycleOwner = this

        //Retrieve Values from arguments
        val args = EditProfileFragmentArgs.fromBundle(arguments!!)

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
                saveUserData(
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

        editProfileViewModel.authListener.value = this
        return binding.root
    }
}
