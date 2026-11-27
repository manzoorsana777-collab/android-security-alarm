package com.sanamanzoor.securityalarm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.net.URLEncoder

class WhatsAppAlertHelper(private val context: Context) {

    fun sendEmergencyAlert(phoneNumberWithCountryCode: String, message: String) {
        try {
            val encodedMessage = URLEncoder.encode(message, "UTF-8")
            val uri = Uri.parse("https://wa.me/$phoneNumberWithCountryCode?text=$encodedMessage")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (ex: Exception) {
            Log.e("WhatsAppAlertHelper", "Failed to launch WhatsApp alert intent", ex)
        }
    }
}
