package com.pragyashal.SvaraflowApp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

// Step 1: Implement the PaymentResultWithDataListener interface
class SubscriptionActivity : AppCompatActivity(), PaymentResultWithDataListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)

        // Preload Razorpay for faster processing
        Checkout.preload(applicationContext)

        // Your existing code for buttons and toolbar
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

        // Step 2: Call the new startPayment function from each button listener
        litePlanButton.setOnClickListener {
            // Amount is in paise, so 19900 is ₹199.00
            startPayment("Lite Plan", 79900)
        }
        basicPlanButton.setOnClickListener {
            startPayment("Basic Plan", 160000)
        }
        proPlanButton.setOnClickListener {
            startPayment("Pro Plan", 1200000)
        }
        maxPlanButton.setOnClickListener {
            startPayment("Max Plan", 4500000)
        }
    }

    // Step 3: Create a single, reusable function to start the payment
    private fun startPayment(planName: String, amount: Int) {
        val checkout = Checkout()

        // **IMPORTANT**: Replace with your actual Razorpay Key ID from the dashboard
        checkout.setKeyID("rzp_test_WprqVAEObL47N8")

        try {
            val options = JSONObject()
            options.put("name", "SvaraFlow App")
            options.put("description", "Subscription: $planName")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg") // Your app logo

            // In a real app, you must get this order_id from your server
            // For this example, we'll skip it, but it's crucial for production
            // options.put("order_id", "order_your_id_from_server")

            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", amount.toString()) // Amount in the smallest currency unit (paise)

            val prefill = JSONObject()
            prefill.put("email", "test@example.com")
            prefill.put("contact", "9876543210")
            options.put("prefill", prefill)

            checkout.open(this@SubscriptionActivity, options)

        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    // Step 4: Handle the payment success callback
    override fun onPaymentSuccess(razorpayPaymentId: String?, paymentData: PaymentData?) {
        try {
            val paymentId = paymentData?.paymentId
            Toast.makeText(this, "Payment Successful: $paymentId", Toast.LENGTH_LONG).show()
            // In a real app, you would send the paymentData to your server to verify the signature
            Log.d("PAYMENT_SUCCESS", "ID: ${paymentData?.paymentId}, OrderID: ${paymentData?.orderId}, Sig: ${paymentData?.signature}")

        } catch (e: Exception) {
            Log.e("PAYMENT_ERROR", "Exception in onPaymentSuccess", e)
        }
    }

    // Step 5: Handle the payment failure callback
    override fun onPaymentError(code: Int, description: String?, paymentData: PaymentData?) {
        try {
            Toast.makeText(this, "Payment Failed: $description", Toast.LENGTH_LONG).show()
            Log.e("PAYMENT_FAILED", "Code: $code, Description: $description")
        } catch (e: Exception) {
            Log.e("PAYMENT_ERROR", "Exception in onPaymentError", e)
        }
    }
}