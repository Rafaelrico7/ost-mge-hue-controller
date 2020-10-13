package ch.ost.rj.mge.testat.huelightcontrollerapp.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class File{
    val file = File("./Data/", "hueFileStorage")
    private val gson = Gson()
    init {

    }
    fun loadFileContent (lightList: MutableList<Light> ,ctx: Context){
        val lightListType = object : TypeToken<MutableList<Light>>() {}.type
        lightList.addAll(0,gson.fromJson(ctx.openFileInput("hueFileStorage").bufferedReader(), lightListType))

    }

    fun persistToFile (lightList: MutableList<Light>, ctx: Context){
        ctx.openFileOutput("hueFileStorage", Context.MODE_PRIVATE).bufferedWriter().write(gson.toJson(lightList))
    }
}