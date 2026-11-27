package com.sanamanzoor.securityalarm

import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sanamanzoor.securityalarm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var voiceAssistant: VoiceAssistantManager
    private lateinit var whatsAppHelper: WhatsAppAlertHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        voiceAssistant = VoiceAssistantManager(this)
        whatsAppHelper = WhatsAppAlertHelper(this)

        binding.btnTriggerAlarm.setOnClickListener {
            binding.tvStatus.text = "Status: Alarm active — verify eyes open & smile"
            voiceAssistant.speak("Alarm active. Please open your eyes and smile to dismiss.")
            playDefaultAlarmTone()
        }

        binding.btnVoiceCommand.setOnClickListener {
            binding.tvStatus.text = "Status: Listening for voice command..."
            voiceAssistant.speak("Voice interaction ready.")
        }

        binding.btnSendAlert.setOnClickListener {
            binding.tvStatus.text = "Status: Preparing automated WhatsApp alert..."
            whatsAppHelper.sendEmergencyAlert(
                phoneNumberWithCountryCode = "923000000000",
                message = "Security Alert: Unauthorized attempt or emergency trigger detected."
            )
        }
    }

    private fun playDefaultAlarmTone() {
        val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        RingtoneManager.getRingtone(applicationContext, alarmUri)?.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        voiceAssistant.shutdown()
    }
}
