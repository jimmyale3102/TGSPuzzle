package com.android.uptc.tgspuzzleproject.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.logic.GlobalValues
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.alert_level_game.view.*
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

                drawerView.username.text = GlobalValues.username

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
                    GlobalValues.scoreType
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

    private fun initComponents() {
        configNavigation()
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_level_game, null)
        val builder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = builder.create()

        alertDialog.setCancelable(false)
        // Set width alert
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val displayWidth: Int = displayMetrics.widthPixels
        val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(alertDialog.window?.attributes)
        val dialogWindowWidth = (displayWidth * 0.8f).toInt()
        layoutParams.width = dialogWindowWidth
        alertDialog.window?.attributes = layoutParams

        cross_word_content.setOnClickListener {
            alertDialog.show()
            dialogView.easy_button.setOnClickListener {
                GlobalValues.levelGame = EASY
                startActivity(Intent(this, CrossWordActivity::class.java))
                alertDialog.dismiss()
            }
            dialogView.hard_button.setOnClickListener {
                GlobalValues.levelGame = HARD
                startActivity(Intent(this, CrossWordActivity::class.java))
                alertDialog.dismiss()
            }
        }
        search_word_content.setOnClickListener {
            alertDialog.show()
            dialogView.easy_button.setOnClickListener {
                GlobalValues.levelGame = EASY
                startActivity(Intent(this, SearchWordActivity::class.java))
                alertDialog.dismiss()
            }
            dialogView.hard_button.setOnClickListener {
                GlobalValues.levelGame = HARD
                startActivity(Intent(this, SearchWordActivity::class.java))
                alertDialog.dismiss()
            }
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

    companion object {
        const val EASY = 0
        const val HARD = 1
        const val EASY_CROSSWORD = 1
        const val HARD_CROSSWORD = 2
        const val EASY_SEARCH_WORD = 3
        const val HARD_SEARCH_WORD = 4
    }
}