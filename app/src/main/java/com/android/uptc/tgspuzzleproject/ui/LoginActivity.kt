package com.android.uptc.tgspuzzleproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.extensions.snack
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun emailAndPasswordSignIn() {
        if (username.text.toString().isNotEmpty()
            && password.text.toString().isNotEmpty()
        ) {
            auth.signInWithEmailAndPassword(
                username.text.toString(),
                password.text.toString()
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        signIn()
                    } else {
                        // If sign in fails, display a message to the user.
                        when(task.exception) {
                            is FirebaseAuthWeakPasswordException ->
                                login_button.snack(R.string.invalid_password)
                            is FirebaseAuthInvalidUserException ->
                                login_button.snack(R.string.invalid_user)
                            is FirebaseAuthInvalidCredentialsException ->
                                login_button.snack(R.string.invalid_or_null_password)
                            else -> login_button.snack(R.string.authentication_failed)
                        }
                    }
                }
        } else {
            if(username.text.toString().isEmpty()) {
                username_parent.error = getString(R.string.empty_username)
            }
            if(password.text.toString().isEmpty()) {
                password_parent.error = getString(R.string.empty_password)
            }
            login_layout.snack(R.string.complete_information_message)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    signIn()
                } else {
                    // If sign in fails, display a message to the user.
                    when(task.exception) {
                        is FirebaseAuthUserCollisionException ->
                            login_layout.snack(R.string.account_exists_message)
                        else -> login_layout.snack(R.string.authentication_failed)
                    }
                }
            }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private fun signIn() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun initComponents() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        google_login.setOnClickListener { googleSignIn() }
        login_button.setOnClickListener { emailAndPasswordSignIn() }
        register_button.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        username.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(sequence!!.toString().isNotEmpty()) {
                    username_parent.error = null
                }
            }
        })
        password.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(sequence!!.toString().isNotEmpty()) {
                    password_parent.error = null
                }
            }
        })
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}