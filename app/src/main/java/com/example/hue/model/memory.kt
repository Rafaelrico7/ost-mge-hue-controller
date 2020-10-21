package com.example.hue.model

import android.content.Context
import android.util.Log
import com.example.hue.api.*
import com.google.gson.Gson
import org.json.JSONObject

class Memory (ctx: Context){
    private var lightList: MutableList<Light> = mutableListOf(Light())
    private var zoneList: MutableList<Zone> = mutableListOf(Zone("default"))
    private var authUser: String = "halloMueter"
    private val file = File()
    private val api = ApiRoute()
    private var ipAdrr: String = "192.168.0.1"
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

    fun getLights(ctx: Context): List<Light>{
        if(lightList.isEmpty()){
            api.eval(GetLights(ipAdrr, authUser, ctx) { res: String ->
                Log.i("SCUP", res)
                val jObj = JSONObject(res)
                var index = 1
                while (jObj.has("$index")){
                    val light = gson.fromJson(jObj.getJSONObject("$index").getJSONObject("state").toString(2), Light::class.java)
                    light?.toString()?.let { lightList.add(light) }
                    index++
                }
            })
        }
        return lightList
    }

    fun getUser(ctx: Context): String{
        Log.i("SCUP", "getUser")
        if (authUser.isEmpty() && ipAdrr.isNotEmpty()){
            Log.i("SCUP", "before eval")
            api.eval(GetUser(ipAdrr,"Test", ctx) { res: String ->
                Log.i("SCUP", res)
                val jObj = JSONObject(res)
                authUser = jObj.getJSONObject("success").getString("username")
                Log.i("SCUP", "AuthUser gesetzt {$authUser}")
            })
        }
        return authUser
    }

    fun setLightStatus(index: Int, ctx: Context){
        Log.i("SCUP", "setLightStatus")
        if(lightList.isNotEmpty() && authUser.isNotEmpty()){
            Log.i("SCUP", "Aufruf setLightStatus")
            api.eval(SetLightStatus(ipAdrr, authUser, lightList[index], ctx, index))
        }
    }

    fun getLight(index: Int, ctx: Context): Light{
        if (lightList.isEmpty()){
            getLights(ctx)
        }
        return lightList[index]
    }

    fun setIpAddr(ipAddr: String){
        ipAdrr = ipAddr
    }

}