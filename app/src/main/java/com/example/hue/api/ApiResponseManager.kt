package com.example.hue.api

import android.content.Context
import com.example.hue.model.Light
import org.json.JSONObject

data class GetUser(val ipAdress: String, val devicetype: String, val ctx: Context, val callback: ((res: JSONObject) -> Unit)) : SApiRoute()
data class GetLights(val ipAdress: String, val authUser: String, val ctx: Context, val callback: ((res: JSONObject) -> Unit)) : SApiRoute()
data class GetLight(val ipAdress: String, val authUser: String, var lightIdx: Int, val ctx: Context, val callback: ((res: JSONObject) -> Unit)) : SApiRoute()
data class SetLightStatus(val ipAdress: String, val authUser: String, val status: Light, val ctx: Context, var lightIdx: Int) : SApiRoute()
data class TurnLightOnOff(val ipAdress: String, val authUser: String, val status: Light, val ctx: Context, var lightIdx: Int) : SApiRoute()

sealed class SApiRoute