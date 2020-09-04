package com.example.twilio

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.net.TrafficStats
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.telephony.SmsManager
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.IOException
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    val ACCOUNT_SID = "ACe2de2709947c015e947ff9411997a480"
    val AUTH_TOKEN = "5136df8b0a2bc2feabe5e42fb96c3a8b"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StrictMode.enableDefaults()
        val MY_PERMISSION_CODE=1
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                MY_PERMISSION_CODE
            )
        }
        button.setOnClickListener {

         sendsms()

        }
        button2.setOnClickListener {
            sendsmswithphone()
        }

    }

    private fun sendsmswithphone() {
        /*val phoneNumber = "03137146512"
        val message =
            ("Hello World! Now we are going to demonstrate " + "how to send a message with more than 160 characters from your Android application.")
        val smsManager = SmsManager.getDefault()
        val parts = smsManager.divideMessage(message)
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)*/
        var intent =  Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "03341617255"));
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {


            startActivity(intent)
            val audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager

            audioManager.isSpeakerphoneOn = true
            audioManager.mode = AudioManager.MODE_IN_CALL
        }

    }


    fun sendsms()
    {
        TrafficStats.setThreadStatsTag(1)
        var message="i"
        var fromPhoneNumber="+12053460507"
        var toPhoneNumber="+923341617255"
        val client = OkHttpClient()
        val url = "https://api.twilio.com/2010-04-01/Accounts/"+ACCOUNT_SID+"/Messages"
        val base64EncodedCredentials = "Basic " + Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).toByteArray(), Base64.NO_WRAP)
        val body = FormBody.Builder()
            .add("From", fromPhoneNumber)
            .add("To", toPhoneNumber)
            .add("Body", message)
            .build()
        val request = Request.Builder()
            .url(url)
            .post(body)
            .header("Authorization", base64EncodedCredentials)
            .build()
        try
        {

                val response = client.newCall(request).execute()
                Log.d("done", "sendSms: " + response.body().string())


        }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }

}
