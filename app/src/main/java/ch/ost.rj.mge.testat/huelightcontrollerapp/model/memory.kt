package ch.ost.rj.mge.testat.huelightcontrollerapp.model

import android.content.Context

class memory (ctx: Context){
    private val lightList: MutableList<Light> = mutableListOf(Light())
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
}