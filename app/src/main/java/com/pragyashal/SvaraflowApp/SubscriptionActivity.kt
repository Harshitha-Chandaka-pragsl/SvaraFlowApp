package com.pragyashal.SvaraflowApp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class SubscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        val proPlanButton: Button = findViewById(R.id.proPlanButton)
        val litePlanButton: Button = findViewById(R.id.litePlanButton)
        val basicPlanButton: Button = findViewById(R.id.basicPlanButton)
        val maxPlanButton: Button = findViewById(R.id.maxPlanButton)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        litePlanButton.setOnClickListener {
            Toast.makeText(this, "Starting payment for Lite plan...", Toast.LENGTH_SHORT).show()
        }
        basicPlanButton.setOnClickListener {
            Toast.makeText(this, "Starting payment for Basic plan...", Toast.LENGTH_SHORT).show()
        }
        proPlanButton.setOnClickListener {
            Toast.makeText(this, "Starting payment for Pro plan...", Toast.LENGTH_SHORT).show()
        }
        maxPlanButton.setOnClickListener {
            Toast.makeText(this, "Starting payment for Max plan...", Toast.LENGTH_SHORT).show()
        }
    }
}
