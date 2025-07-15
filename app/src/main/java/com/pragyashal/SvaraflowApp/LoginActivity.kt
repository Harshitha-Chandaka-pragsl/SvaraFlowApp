package com.pragyashal.SvaraflowApp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton: Button = findViewById(R.id.loginButton)
        val signUpTextView: TextView = findViewById(R.id.signUpTextView)
        val googleSignInButton: Button = findViewById(R.id.googleSignInButton)

        loginButton.setOnClickListener {
            val intent = Intent(this, TTSActivity::class.java)
            startActivity(intent)
            finish()
        }

        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        googleSignInButton.setOnClickListener {
            Toast.makeText(this, "Google Sign-In clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}