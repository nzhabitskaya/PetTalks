package com.mobile.android.chameapps.pettalks

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    private var toolbar: Toolbar? = null

    private lateinit var prefs: SharedPreferences
    private var isDemo: Boolean = false
    private var isCC: Boolean = false
    private var isVoice: Boolean = false
    private var isTrainingMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initToolbar()
        initNavigationMenu()
        prefs = getSharedPreferences("com.mobile.android.chameapps.pettalks", Context.MODE_PRIVATE)
        clearPreferences()
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

                readPreferences()
                when (item.itemId) {
                    R.id.menu_camera_demo -> {
                        if (isDemo) {
                            setFragment(
                                DemoFragment.newInstance(
                                    isCC,
                                    isVoice,
                                    isTrainingMode
                                ) as Fragment
                            )
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

    private fun clearPreferences() {
        prefs.edit().clear().commit()
    }

    private fun readPreferences() {
        isDemo = prefs.getBoolean(R.id.menu_camera_demo.toString(), false)
        isCC = prefs.getBoolean(R.id.menu_cc.toString(), false)
        isVoice = prefs.getBoolean(R.id.menu_voice.toString(), false)
        isTrainingMode = prefs.getBoolean(R.id.menu_training_mode.toString(), false)

        Log.e("ABC", "Read prefs: " + R.id.menu_camera_demo.toString() + " = " + isDemo)
        Log.e("ABC", "Read prefs: " + R.id.menu_cc.toString() + " = " + isCC)
        Log.e("ABC", "Read prefs: " + R.id.menu_voice.toString() + " = " + isVoice)
        Log.e("ABC", "Read prefs: " + R.id.menu_training_mode.toString() + " = " + isTrainingMode)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}