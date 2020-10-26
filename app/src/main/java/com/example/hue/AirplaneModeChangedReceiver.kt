package com.example.hue

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AirplaneModeChangedReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return
        if(isAirplaneModeEnabled){
            Toast.makeText(context, "Airplane Mode is enabled, the Hue Controller needs Internet Connection to work properly!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Airplane Mode was disabled, the Hue Controller should work now!", Toast.LENGTH_SHORT).show()
        }
    }
}