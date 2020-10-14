package com.example.hue.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class File{

    private val gson = Gson()
    init {

    }
    fun loadFileContent (lightList: MutableList<Light>, ctx: Context){
        val lightListType = object : TypeToken<MutableList<Light>>() {}.type
        val file = File( "hueFileStorage")
        if (file.exists()) {
            val lights: MutableList<Light> =
                gson.fromJson(ctx.openFileInput("hueFileStorage").bufferedReader(), lightListType)
            if (lights.size > 0) {
                lightList.addAll(0, lights)
            }
        }

    }

    fun persistToFile (lightList: MutableList<Light>, ctx: Context){
        ctx.openFileOutput("hueFileStorage", Context.MODE_PRIVATE).bufferedWriter().write(gson.toJson(lightList))
    }
}