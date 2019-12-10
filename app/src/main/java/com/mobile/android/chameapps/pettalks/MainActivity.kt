package com.mobile.android.chameapps.pettalks

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mobile.android.chameapps.pettalks.camera.Camera2VideoFragment
import com.mobile.android.chameapps.pettalks.demo.DemoFragment
import com.mobile.android.chameapps.pettalks.profile.ProfileFragment
import com.mobile.android.chameapps.pettalks.R


class MainActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    private var toolbar: Toolbar? = null

    private var isDemo: Boolean = false
    private var isCC: Boolean = false
    private var isVoice: Boolean = false
    private var isTrainingMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initToolbar()
        initNavigationMenu()
        setFragment(Camera2VideoFragment.newInstance())
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setHomeButtonEnabled(true)
        actionBar!!.setTitle(null);
    }

    private fun initNavigationMenu() {
        val nav_view = findViewById(R.id.nav_view) as NavigationView
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = object : ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {}
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                drawer.closeDrawers()

                when (item.itemId) {
                    R.id.menu_camera_demo -> {
                        if(isDemo) {
                            setFragment(DemoFragment.newInstance(isCC, isVoice, isTrainingMode) as Fragment)
                        } else {
                            setFragment(Camera2VideoFragment.newInstance() as Fragment)
                        }
                        return true
                    }
                    R.id.menu_cc -> {
                        setFragment(DemoFragment.newInstance(isCC, isVoice, isTrainingMode))
                        return true
                    }
                    R.id.menu_voice -> {
                        setFragment(DemoFragment.newInstance(isCC, isVoice, isTrainingMode))
                        return true
                    }
                    R.id.menu_training_mode -> {
                        setFragment(DemoFragment.newInstance(isCC, isVoice, isTrainingMode))
                        return true
                    }
                    R.id.menu_profile -> {
                        setFragment(ProfileFragment.newInstance())
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_camera_demo -> {
                isDemo = item.isChecked
                true
            }
            R.id.menu_cc -> {
                isCC = item.isChecked
                true
            }
            R.id.menu_voice -> {
                isVoice = item.isChecked
                true
            }
            R.id.menu_training_mode -> {
                isTrainingMode = item.isChecked
                true
            }
            else -> false
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}