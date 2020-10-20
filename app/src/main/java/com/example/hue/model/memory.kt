package com.example.hue.model

import android.content.Context
import android.util.Log
import com.example.hue.api.ApiRoute
import com.example.hue.api.GetLights
import com.example.hue.api.GetUser
import com.example.hue.api.SApiRoute
import com.google.gson.Gson

class Memory (ctx: Context){
    private var lightList: MutableList<Light> = mutableListOf(Light())

    private var authUser: String = ""
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
                lightList = gson.fromJson(res, LightList::class.java).list
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
                authUser = res
            })
        }
        return authUser
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