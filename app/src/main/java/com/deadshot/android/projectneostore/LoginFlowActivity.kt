package com.deadshot.android.projectneostore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.deadshot.android.projectneostore.databinding.ActivityLoginFlowBinding

class LoginFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_flow)
    }
}