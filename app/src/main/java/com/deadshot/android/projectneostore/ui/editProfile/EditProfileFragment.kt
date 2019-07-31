package com.deadshot.android.projectneostore.ui.editProfile


import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders

import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.StoreFlowActivity
import com.deadshot.android.projectneostore.databinding.FragmentEditProfileBinding
import com.deadshot.android.projectneostore.databinding.FragmentMyAccountBinding
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import timber.log.Timber
import java.util.*


class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Add Timber to fragment
        Timber.plant(Timber.DebugTree())

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
        binding.lifecycleOwner = this

        editProfileViewModel = ViewModelProviders.of(this).get(EditProfileViewModel::class.java)
        binding.editProfileViewModel = editProfileViewModel



        binding.etDobInputLayout.setEndIconOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(   activity as StoreFlowActivity, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                editProfileViewModel.dob = "$mDay-$mMonth-$mYear"
                binding.invalidateAll()
            },year, month, day)

            datePickerDialog.show()
        }

        return binding.root
    }


}
