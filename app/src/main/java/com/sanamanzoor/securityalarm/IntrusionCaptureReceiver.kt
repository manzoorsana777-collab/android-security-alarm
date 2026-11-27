package com.sanamanzoor.securityalarm

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class IntrusionCaptureReceiver : DeviceAdminReceiver() {

    override fun onPasswordFailed(context: Context, intent: Intent) {
        super.onPasswordFailed(context, intent)
        Log.w("IntrusionCapture", "Incorrect PIN/pattern attempt detected.")

        // Trigger front-camera intrusion capture flow and automated family alert
        val alertHelper = WhatsAppAlertHelper(context)
        alertHelper.sendEmergencyAlert(
            phoneNumberWithCountryCode = "923000000000",
            message = "Intrusion Alert: Incorrect PIN/pattern detected on Android device."
        )
    }
}
