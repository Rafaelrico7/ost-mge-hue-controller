package com.example.hue.model

import android.content.Context

class Memory (ctx: Context){
    private val lightList: MutableList<Light> = mutableListOf(Light())
    private val authUser: String = ""
    private val file = File()
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

    fun getLights(): List<Light>{
        return lightList
    }

    fun getUser(): String{
        if (authUser.isEmpty()){
            return ""
        }else{
            return authUser
        }
    }

}