package com.example.words

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.words.ui.home.HomeFragment
import com.example.words.ui.update.UpdateFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val navView: BottomNavigationView = findViewById(R.id.nav_view)
        //val navController = findNavController(R.id.nav_host_fragment)
        //val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_update, R.id.navigation_notifications))

        //setupActionBarWithNavController(navController, appBarConfiguration)
        //nav_view.setupWithNavController(navController)

        setListeners()

        nav_view.selectedItemId = R.id.navigation_home
    }


    fun setListeners() {
        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    /*toolbar.search.visibility = View.VISIBLE
                    toolbar.alert.visibility = View.VISIBLE*/
                    val fragment = HomeFragment.newInstance()
                    openFragment(fragment)
                    true
                }

                R.id.navigation_update -> {
                    val fragment = UpdateFragment.newInstance()
                    openFragment(fragment)
                    true
                }

                else -> false
            }
        }
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}