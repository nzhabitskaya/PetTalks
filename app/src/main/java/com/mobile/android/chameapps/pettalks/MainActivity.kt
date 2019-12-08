package com.mobile.android.chameapps.pettalks

import android.graphics.Color
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    private var toolbar: Toolbar? = null

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
                //actionBar!!.setTitle(item.title)
                drawer.closeDrawers()

                when (item.itemId) {
                    R.id.menu_camera_demo -> {
                        setFragment(Camera2VideoFragment.newInstance() as Fragment)
                        return true
                    }
                    R.id.menu_cc -> {
                        setFragment(DemoFragment.newInstance())
                        return true
                    }
                    R.id.menu_about -> {
                        setFragment(DemoFragment.newInstance())
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}