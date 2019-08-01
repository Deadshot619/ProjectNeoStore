package com.deadshot.android.projectneostore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.deadshot.android.projectneostore.databinding.ActivityStoreFlowBinding
import com.deadshot.android.projectneostore.utils.EMAIL
import com.deadshot.android.projectneostore.utils.FIRST_NAME
import com.deadshot.android.projectneostore.utils.LAST_NAME
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import io.paperdb.Paper
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class StoreFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreFlowBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_flow)
        Paper.init(applicationContext)

        loadData()
        /**
         * add Drawer Layout
         */
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostStoreFlowFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        /**
         * Add Name & email to NavHeader
         */
        with(binding.navView.getHeaderView(0)){
            tv_userRealName.text = "${firstName.capitalize()} ${lastName.capitalize()}"
            tv_userEmail.text = email
            invalidate()
        }
        
        /**
         * Code to hide Nav Drawer in destinations other than start destination
         */
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (destination.id == controller.graph.startDestination)
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            else
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    fun loadData(){
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE) ?: return
        firstName = sharedPreferences.getString(FIRST_NAME, getString(R.string.default_value))
        lastName = sharedPreferences.getString(LAST_NAME, getString(R.string.default_value))
        email = sharedPreferences.getString(EMAIL, getString(R.string.default_value))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostStoreFlowFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
