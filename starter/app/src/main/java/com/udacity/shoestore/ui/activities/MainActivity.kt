package com.udacity.shoestore.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private var navOptions: NavOptions? = null
    var navController: NavController? = null
    var mBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding?.toolbar);
        navController = findNavController(this, R.id.consumer_nav_fragment)
        navController?.navigate(R.id.loginFragment)

        val appBarConfiguration = AppBarConfiguration(navController?.graph!!)

        mBinding?.toolbar?.setupWithNavController(navController!!, appBarConfiguration)

        navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()

        Timber.plant(Timber.DebugTree())
    }

    fun openFragment(navController: NavController,
                     navigationId: Int, bundle: Bundle?) {
        navController.navigate(navigationId,
            bundle, navOptions)
    }
}
