package com.android.uptc.tgspuzzleproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.android.uptc.tgspuzzleproject.R
import com.android.uptc.tgspuzzleproject.extensions.snack
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.login_button
import kotlinx.android.synthetic.main.activity_register.password
import kotlinx.android.synthetic.main.activity_register.password_parent
import kotlinx.android.synthetic.main.activity_register.register_button
import kotlinx.android.synthetic.main.activity_register.username
import kotlinx.android.synthetic.main.activity_register.username_parent

class RegisterActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initComponents()
    }

    private fun initComponents() {
        register_button.setOnClickListener {
            if (username.text.toString().isNotEmpty()
                || password.text.toString().isNotEmpty()
                || reply_password.text.toString().isNotEmpty()
            ) {
                auth.createUserWithEmailAndPassword(
                    username.text.toString(),
                    password.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            register_layout.snack(R.string.user_register_successful)
                        } else {
                            // If sign in fails, display a message to the user.
                            when(task.exception) {
                                is FirebaseAuthWeakPasswordException -> {
                                    register_layout.snack(R.string.invalid_password)
                                }
                                is FirebaseAuthUserCollisionException -> {
                                    register_layout.snack(R.string.account_exists_message)
                                }
                                else -> register_layout.snack(R.string.authentication_failed)
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
                if(reply_password.text.toString().isEmpty()) {
                    reply_password_parent.error = getString(R.string.empty_password)
                }
                register_layout.snack(R.string.complete_information_message)
            }
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
        reply_password.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(sequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(sequence!!.toString().isNotEmpty()) {
                    reply_password_parent.error = null
                }
            }
        })
        login_button.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).apply{
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            })
            finish()
        }
    }
}