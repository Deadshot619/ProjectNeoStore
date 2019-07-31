package com.deadshot.android.projectneostore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.deadshot.android.projectneostore.databinding.ActivityStoreFlowBinding
import io.paperdb.Paper

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
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

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

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostStoreFlowFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
