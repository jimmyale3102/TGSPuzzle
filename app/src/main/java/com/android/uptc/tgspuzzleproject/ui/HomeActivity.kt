package com.android.uptc.tgspuzzleproject.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.android.uptc.tgspuzzleproject.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_menu_header.view.*

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        initComponents()
    }

    private fun initComponents() {
        configNavigation()
    }

    private fun configNavigation() {
        val drawerToggle = object: ActionBarDrawerToggle(
            this,
            home_layout,
            home_toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                //toast("Drawer closed")
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

                //toast("Drawer opened")
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)

                drawerView.username.text = "User"
                drawerView.user_score.text = "0 pts"

            }
        }
        // Configure the drawer alert_loading to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        home_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Set navigation view navigation item selected listener
        accompanists_navigation.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.crossword_score_item -> {
                    //showFirstAidFilter()
                }
                R.id.search_word_item -> {
                    //showAgeFilter()
                }
                R.id.about_item -> {
                    //showGenderFilter()
                }
                R.id.logout_item -> logout()
            }
            true
        }
        home_layout.closeDrawer(GravityCompat.START)
    }

    private fun logout() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.crossword_score_item -> return true
            R.id.search_word_item -> return true
            R.id.about_item -> return true
            R.id.logout_item -> return true
        }
        return false
    }
}