package com.pragyashal.SvaraflowApp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.imageview.ShapeableImageView

class ProfileActivity : AppCompatActivity() {

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val profileImageView: ShapeableImageView = findViewById(R.id.profileImageView)
            profileImageView.setImageURI(it)
            Toast.makeText(this, "Profile photo updated!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        val profileImageView: ShapeableImageView = findViewById(R.id.profileImageView)
        val manageAccountTextView: TextView = findViewById(R.id.manageAccountTextView)
        val helpTextView: TextView = findViewById(R.id.helpTextView)
        val notificationsTextView: TextView = findViewById(R.id.notificationsTextView)
        val feedbackTextView: TextView = findViewById(R.id.feedbackTextView)
        val signOutTextView: TextView = findViewById(R.id.signOutTextView)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        profileImageView.setOnClickListener {
            pickImage.launch("image/*")
        }

        manageAccountTextView.setOnClickListener {
            Toast.makeText(this, "Manage Account clicked", Toast.LENGTH_SHORT).show()
        }

        helpTextView.setOnClickListener {
            Toast.makeText(this, "Help and support clicked", Toast.LENGTH_SHORT).show()
        }

        notificationsTextView.setOnClickListener {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show()
        }

        feedbackTextView.setOnClickListener {
            Toast.makeText(this, "Send feedback clicked", Toast.LENGTH_SHORT).show()
        }

        signOutTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
