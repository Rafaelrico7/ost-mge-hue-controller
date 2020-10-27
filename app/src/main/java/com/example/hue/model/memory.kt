package com.example.hue.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.hue.api.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.json.JSONObject

class Memory (ctx: Context){
    private var lightList = mutableListOf<Light>()
    private var zoneList: MutableList<Zone> = mutableListOf(Zone("default"))
    private var authUser: String = "7YFFIu9reI2O9yMAcfxYgE7RYZWF7N-q4ETuXlEr"
    private val file = File()
    private val api = ApiRoute()
    private var ipAdrr: String = "192.168.50.149"
    private var gson = Gson()
    init {
        file.loadFileContent(lightList, ctx)

    }

    fun persistLights(ctx: Context) {
        file.persistToFile(lightList, ctx)
    }

    fun addLight(light: Light) {
        lightList.add(light)
    }

    fun removeLight(light: Light) {
        lightList.remove(light)
    }

    fun getLight(name: String): Light? {
        lightList.forEach{ light -> if(light.name == name){ return light} }
        return null
    }
    fun getLocalLights() : List<Light>{
        return lightList
    }
    suspend fun getLights(ctx: Context, callback: (res: List<Light>) -> Unit) {
        withContext(Dispatchers.IO) {
            val localList = mutableListOf<Light>()
            api.eval(GetLights(ipAdrr, authUser, ctx) { res: JSONObject ->
                Log.i("SCUP", res.toString())
                var index = 1
                while (res.has("$index")) {
                    val light = gson.fromJson(
                        res.getJSONObject("$index").getJSONObject("state").toString(2),
                        Light::class.java
                    )
                    light.index = index
                    light?.toString()?.let { localList.add(light) }
                    index++
                }
                Log.i("SCUP", "Setze LightListe")
                lightList = localList
                callback(lightList)
            })
        }
    }

    suspend fun getUser(ctx: Context): String {
        Log.i("SCUP", "getUser")
        if (authUser.isEmpty() && ipAdrr.isNotEmpty()) {
            Log.i("SCUP", "before eval")
            withContext(Dispatchers.IO) {
                api.eval(GetUser(ipAdrr, "Test", ctx) { res: JSONObject ->
                    Log.i("SCUP", res.toString())
                    if (res.has("success")) {
                        authUser = res.getJSONObject("success").getString("username")
                        Log.i("SCUP", "AuthUser gesetzt {$authUser}")
                    } else {
                        Log.e("SCUP", res.toString())
                    }
                })
            }
        }
        return authUser
    }

    suspend fun setLightsStatus(lightOn: Boolean, ctx: Context) {
        Log.i("SCUP", "setLightStatus")
        if (lightList.isNotEmpty() && authUser.isNotEmpty()) {
            Log.i("SCUP", "Aufruf setLightStatus")
            withContext(Dispatchers.Default) {
                lightList.forEach(action = { light ->
                    if (light.on != lightOn) {
                        light.on = lightOn
                        api.eval(TurnLightOnOff(ipAdrr, authUser, light, ctx, light.index))
                    }
                })
            }
        }
    }

    suspend fun setLightStatus(light: Light, ctx: Context) {
        Log.i("SCUP", "setLightStatus")
        if (lightList.isNotEmpty() && authUser.isNotEmpty()) {
            Log.i("SCUP", "Aufruf setLightStatus")
            withContext(Dispatchers.Default) {
                    if (light.on) {
                        api.eval(SetLightStatus(ipAdrr, authUser, light, ctx, light.index))
                    }
            }
        }
    }


    fun getLight(index: Int, ctx: Context): Light{
        return lightList[index]
    }

    fun setIpAddr(ipAddr: String){
        ipAdrr = ipAddr
    }

}