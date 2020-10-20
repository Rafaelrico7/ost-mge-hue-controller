package com.example.hue.api

import android.content.Context
import com.example.hue.model.Light

//data class Login(var email: String, var password: String, var ctx: Context) : SApiRoute()
data class GetUser(val ipAdress: String, val devicetype: String, val ctx: Context, val callback: ((res: String) -> Unit)? = null) : SApiRoute()
data class GetLights(val ipAdress: String, val authUser: String, val ctx: Context, val callback: ((res: String) -> Unit)? = null) : SApiRoute()
data class GetLight(val ipAdress: String, var light: Number, val ctx: Context, val callback: ((res: String) -> Unit)? = null) : SApiRoute()
data class SetLightStatus(val ipAdress: String, val status: Light, val ctx: Context, val callback: ((res: String) -> Unit)? = null) : SApiRoute()

sealed class SApiRoute