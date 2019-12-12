package com.mobile.android.chameapps.pettalks.main.impl

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
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
import com.mobile.android.chameapps.pettalks.R
import com.mobile.android.chameapps.pettalks.application.MyApplication
import com.mobile.android.chameapps.pettalks.camera.Camera2VideoFragment
import com.mobile.android.chameapps.pettalks.demo.impl.DemoFragment
import com.mobile.android.chameapps.pettalks.main.MainContract
import com.mobile.android.chameapps.pettalks.profile.impl.ProfileFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity(),
    MainContract.View {

    @Inject
    lateinit var presenter: MainContract.Presenter

    private var actionBar: ActionBar? = null
    private var toolbar: Toolbar? = null

    private lateinit var prefs: SharedPreferences
    private var isDemo: Boolean = false
    private var isCC: Boolean = false
    private var isVoice: Boolean = false
    private var isTrainingMode: Boolean = false

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        injectDependency()
        presenter.attach(this)
        presenter.registerToggleListeners()
        presenter.startHideMenuCounter()

        initToolbar()
        initNavigationMenu()
        prefs = getSharedPreferences("com.mobile.android.chameapps.pettalks", Context.MODE_PRIVATE)
        prefs.registerOnSharedPreferenceChangeListener(sharedPrefsListener)

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
        drawerLayout = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {}
        drawerLayout.setDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                drawerLayout.closeDrawers()
                readPreferences()
                when (item.itemId) {
                    R.id.menu_profile -> {
                        openProfileScreen()
                        return true
                    }
                }
                return false
            }
        })
    }

    private fun clearPreferences() {
        prefs.edit().clear().apply()
    }

    private fun readPreferences() {
        isDemo = prefs.getBoolean(R.id.menu_camera_demo.toString(), false)
        isCC = prefs.getBoolean(R.id.menu_cc.toString(), false)
        isVoice = prefs.getBoolean(R.id.menu_voice.toString(), false)
        isTrainingMode = prefs.getBoolean(R.id.menu_training_mode.toString(), false)
    }

    override fun openCameraScreen() {
        setFragment(Camera2VideoFragment.newInstance() as Fragment)
    }

    override fun openDemoScreen() {
        setFragment(
            DemoFragment.newInstance(
                isCC,
                isVoice,
                isTrainingMode
            ) as Fragment
        )
    }

    override fun openProfileScreen() {
        setFragment(ProfileFragment.newInstance())
    }

    override fun hideMenu() {
        drawerLayout.closeDrawers()
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    var sharedPrefsListener =
        OnSharedPreferenceChangeListener { sharedPreferences, key ->
            isDemo = prefs.getBoolean(R.id.menu_camera_demo.toString(), false)
            presenter.updateDemoToggle(isDemo)
            isCC = prefs.getBoolean(R.id.menu_cc.toString(), false)
            presenter.updateCcToggle(isCC)
            isVoice = prefs.getBoolean(R.id.menu_voice.toString(), false)
            presenter.updateVoiceToggle(isVoice)
            isTrainingMode = prefs.getBoolean(R.id.menu_training_mode.toString(), false)
            presenter.updateTrainingModeToggle(isTrainingMode)
        }

    private fun injectDependency() {
        (application as MyApplication).getAppComponent(this)!!.inject(this)
    }
}