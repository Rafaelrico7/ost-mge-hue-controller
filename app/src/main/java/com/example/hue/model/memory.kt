package com.example.hue.model

import android.content.Context
import android.util.Log
import com.example.hue.api.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject

class Memory (ctx: Context){
    private var lightList: MutableList<Light> = mutableListOf(Light())
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

    suspend fun getLights(ctx: Context): List<Light>{
            api.eval(GetLights(ipAdrr, authUser, ctx) { res: JSONObject ->
                Log.i("SCUP", res.toString())
                var index = 1
                while (res.has("$index")){
                    val light = gson.fromJson(res.getJSONObject("$index").getJSONObject("state").toString(2), Light::class.java)
                    light.index = index
                    light?.toString()?.let { lightList.add(light) }
                    index++
                }
            })
        return lightList

    }

    suspend fun getUser(ctx: Context): String{
        Log.i("SCUP", "getUser")
        if (authUser.isEmpty() && ipAdrr.isNotEmpty()){
            Log.i("SCUP", "before eval")
            api.eval(GetUser(ipAdrr,"Test", ctx) { res: JSONObject ->
                Log.i("SCUP", res.toString())
                if(res.has("success")) {
                    authUser = res.getJSONObject("success").getString("username")
                    Log.i("SCUP", "AuthUser gesetzt {$authUser}")
                }else {
                    Log.e("SCUP", res.toString())
                }
            })
        }
        return authUser
    }

   suspend fun setLightStatus(iLightList: List<Light>?, ctx: Context, ){
       var localList: List<Light>?
       localList = lightList
       Log.i("SCUP", "setLightStatus")
       if (iLightList != null) {
               localList = iLightList
       }
        if(localList.isNotEmpty() && authUser.isNotEmpty()){
            Log.i("SCUP", "Aufruf setLightStatus")
            localList.forEach(action = {light -> light.on = !light.on
                    api.eval(TurnLightOnOff(ipAdrr, authUser, light, ctx, light.index))
            })

        }
    }


    suspend fun getLight(index: Int, ctx: Context): Light{
        val localList = GlobalScope.async {getLights(ctx)}
        return localList.await()[index]
    }

    fun setIpAddr(ipAddr: String){
        ipAdrr = ipAddr
    }

}