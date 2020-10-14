package com.example.hue.api

import android.content.Context
import com.example.hue.model.Light

//data class Login(var email: String, var password: String, var ctx: Context) : SApiRoute()
data class GetUser(val ipAdress: String, val devicetype: String, var ctx: Context, val callback: (String) -> String) : SApiRoute()
data class GetLights(val ipAdress: String, var ctx: Context, val callback: (String) -> String) : SApiRoute()
data class GetLight(val ipAdress: String, var light: Number, var ctx: Context, val callback: (String) -> String) : SApiRoute()
data class SetLightStatus(val ipAdress: String, var status: Light, var ctx: Context, val callback: (String) -> String) : SApiRoute()

sealed class SApiRoute