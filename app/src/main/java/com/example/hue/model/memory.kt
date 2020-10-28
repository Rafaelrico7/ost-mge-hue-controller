package com.example.hue.model

import android.content.Context
import android.util.Log
import com.example.hue.api.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}

class Memory private constructor(ctx: Context) {
    private var lightList = mutableListOf<Light>()
    private var userSettings : UserSettings
    private val file = File()
    private val api = ApiRoute()
    private var gson = Gson()

    companion object : SingletonHolder<Memory, Context>(::Memory)

    init {
        userSettings = file.loadFileContent(ctx)
        userSettings = UserSettings("7YFFIu9reI2O9yMAcfxYgE7RYZWF7N-q4ETuXlEr","192.168.50.149")
    }

    fun persistSettings(ctx: Context){
        file.persistToFile(userSettings, ctx)
    }

    fun addLight(light: Light) {
        lightList.add(light)
    }

    fun getLocalLights(): List<Light> {
        return lightList
    }

    suspend fun getLights(ctx: Context, callback: (res: List<Light>) -> Unit) {
        withContext(Dispatchers.IO) {
            val localList = mutableListOf<Light>()
            api.eval(GetLights(userSettings.IPAdress, userSettings.authUser, ctx) { res: JSONObject ->
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
        if (userSettings.authUser.isEmpty() && userSettings.IPAdress.isNotEmpty()) {
            Log.i("SCUP", "before eval")
            withContext(Dispatchers.IO) {
                api.eval(GetUser(userSettings.IPAdress, "Test", ctx) { res: JSONObject ->
                    Log.i("SCUP", res.toString())
                    if (res.has("success")) {
                        userSettings.authUser = res.getJSONObject("success").getString("username")
                        Log.i("SCUP", "userSettings.authUser gesetzt {$userSettings.authUser}")
                    } else {
                        Log.e("SCUP", res.toString())
                    }
                })
            }
        }
        return userSettings.authUser
    }

    suspend fun setLightsStatus(lightOn: Boolean, ctx: Context) {
        Log.i("SCUP", "setLightStatus")
        if (lightList.isNotEmpty() && userSettings.authUser.isNotEmpty()) {
            Log.i("SCUP", "Aufruf setLightStatus")
            withContext(Dispatchers.Default) {
                lightList.forEach(action = { light ->
                    if (light.on != lightOn) {
                        light.on = lightOn
                        api.eval(TurnLightOnOff(userSettings.IPAdress, userSettings.authUser, light, ctx, light.index))
                    }
                })
            }
        }
    }

    suspend fun setLightStatus(light: Light, ctx: Context) {
        Log.i("SCUP", "setLightStatus")
        if (lightList.isNotEmpty() && userSettings.authUser.isNotEmpty()) {
            Log.i("SCUP", "Aufruf setLightStatus")
            withContext(Dispatchers.Default) {
                if (light.on) {
                    api.eval(SetLightStatus(userSettings.IPAdress, userSettings.authUser, light, ctx, light.index))
                } else {
                    light.on = true
                    api.eval(TurnLightOnOff(userSettings.IPAdress, userSettings.authUser, light, ctx, light.index))
                    api.eval(SetLightStatus(userSettings.IPAdress, userSettings.authUser, light, ctx, light.index))
                }
            }
        }
    }

    suspend fun toggleLight(light: Light, ctx: Context) {
        Log.i("SCUP", "toggleLight")
        if (lightList.isNotEmpty() && userSettings.authUser.isNotEmpty()) {
            Log.i("SCUP", "Aufruf toggleLight")
            withContext(Dispatchers.Default) {
                api.eval(TurnLightOnOff(userSettings.IPAdress, userSettings.authUser, light, ctx, light.index))
            }
        }
    }

    fun getLight(index: Int, ctx: Context): Light {
        return lightList[index]
    }

    fun setIpAddr(ipAddr: String, ctx: Context) {
        userSettings.IPAdress = ipAddr
        file.persistToFile(userSettings, ctx)
    }
}