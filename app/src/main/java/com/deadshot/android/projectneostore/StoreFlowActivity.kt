package com.deadshot.android.projectneostore

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.deadshot.android.projectneostore.databinding.ActivityStoreFlowBinding
import com.deadshot.android.projectneostore.utils.SHARED_PREFERENCE
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_store_flow.*

class StoreFlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreFlowBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_flow)
        Paper.init(applicationContext)

        /**
         * add Drawer Layout
         */
        drawerLayout = binding.drawerLayout
        val navController = this.findNavController(R.id.myNavHostStoreFlowFragment)
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
        /**
         * Code to hide Nav Drawer in destinations other than start destination
         */
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            val toolBar = supportActionBar ?: return@addOnDestinationChangedListener
            toolBar.setDisplayShowTitleEnabled(true)
            if (destination.id == controller.graph.startDestination)
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            else
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

//            if (destination.id == ){
//                Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show()
//            }

            //Navigate to Login Activity on click of Logout
//            binding.navView.setNavigationItemSelectedListener{
//                if (it.itemId == R.id.logout){
//                    navController.navigate(R.id.loginFlowActivity)
//                    clearSharedPreferences()
//                    finish()
//                    return@setNavigationItemSelectedListener true
//                }
//                return@setNavigationItemSelectedListener true
//            }
        }
    }

    fun onClickLogout(){
        Toast.makeText(this, "Hello", Toast.LENGTH_LONG).show()
    }

    private fun clearSharedPreferences() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCE , Context.MODE_PRIVATE) ?: return
        sharedPreferences.edit().clear().apply()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostStoreFlowFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
