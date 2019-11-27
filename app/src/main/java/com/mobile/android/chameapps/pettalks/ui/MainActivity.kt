package com.mobile.android.chameapps.pettalks

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.mobile.android.chameapps.pettalks.R
import com.mobile.android.chameapps.pettalks.ui.home.impl.HomeFragment

class MainActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initToolbar()
        initNavigationMenu()
        setFragment(HomeFragment.newInstance())
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
                actionBar!!.setTitle(item.title)
                drawer.closeDrawers()

                when (item.itemId) {
                    R.id.tab_for_today -> {
                        setFragment(HomeFragment.newInstance())
                        return true
                    }
                    R.id.tab_my_goals -> {
                        setFragment(HomeFragment.newInstance())
                        return true
                    }
                    R.id.tab_calendar -> {
                        setFragment(HomeFragment.newInstance())
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