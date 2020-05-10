package com.mnsons.offlinebank.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hover.sdk.actions.HoverAction
import com.hover.sdk.api.Hover
import com.hover.sdk.api.Hover.DownloadListener
import com.mnsons.offlinebank.R
import com.mnsons.offlinebank.databinding.ActivityMainBinding
import com.mnsons.offlinebank.utils.ext.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val topLevelDestinationIds = setOf(
        R.id.navigation_home,
        R.id.navigation_activity,
        R.id.navigation_profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Hover.initialize(this)
        Hover.updateActionConfigs(object : DownloadListener {
            override fun onSuccess(p0: ArrayList<HoverAction>?) {
                Toast.makeText(this@MainActivity, p0.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onError(p0: String?) {

            }

        }, this)

        setUpNavDestinationChangeListener()
    }

    inner class DestinationChangeListener : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            binding.toolbar.title = destination.label
            if (topLevelDestinationIds.contains(destination.id).not()) {
                hideBottomTabs()
                animateStatusBarColorChangeTo(R.color.white)
                binding.toolbar.slideUp()
            } else {
                binding.toolbar.slideDown()
                animateStatusBarColorChangeTo(R.color.cardBorderGrey)
                showBottomTabs()
            }
        }
    }


    private fun setUpNavDestinationChangeListener() {
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        binding.toolbar.hide()
        val appBarConfiguration = AppBarConfiguration(topLevelDestinationIds)
        binding.navView.setupWithNavController(navController)
        binding.toolbar.setupWithNavController(
            navController,
            appBarConfiguration
        )
        val destinationChangeListener = DestinationChangeListener()
        navController.addOnDestinationChangedListener(destinationChangeListener)
    }


    private fun hideBottomTabs() {
        if (binding.navView.visibility == View.VISIBLE) {
            binding.navView.slideDown()
        }
    }

    private fun showBottomTabs() {
        if (binding.navView.visibility != View.VISIBLE) {
            binding.navView.slideUp()
        }
    }

}