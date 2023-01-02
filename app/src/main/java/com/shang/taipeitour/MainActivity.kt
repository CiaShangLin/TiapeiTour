package com.shang.taipeitour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.shang.taipeitour.databinding.ActivityMainBinding
import com.shang.taipeitour.responses.AttractionsResponse
import com.shang.taipeitour.view.detail.DetailFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        initNavController()
        initToolbar()
    }

    private fun initNavController() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment? ?: return
        mNavController = host.navController
        mNavController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when (destination.id) {
                    R.id.detailFragment -> {
                        (arguments?.getSerializable(DetailFragment.DATA) as? AttractionsResponse.Data)?.let {
//                            destination.label = it.name
                        }
                    }
                }
            }
        })
    }

    private fun initToolbar() {
        setSupportActionBar(mBinding.toolbar)
        setupActionBarWithNavController(mNavController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return mNavController.navigateUp()
    }
}