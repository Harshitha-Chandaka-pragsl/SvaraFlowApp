package com.pragyashal.SvaraflowApp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val googleSignUpButton: Button = findViewById(R.id.googleSignUpButton)

        signUpButton.setOnClickListener {
            Toast.makeText(this, "Sign Up with email clicked!", Toast.LENGTH_SHORT).show()
        }

        googleSignUpButton.setOnClickListener {
            Toast.makeText(this, "Sign Up with Google clicked!", Toast.LENGTH_SHORT).show()
        }
    }
}
