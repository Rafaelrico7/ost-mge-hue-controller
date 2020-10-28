package com.example.hue.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class File{

    private val gson = Gson()
    fun loadFileContent (ctx: Context) : UserSettings{
        val userSettingsType = object : TypeToken<UserSettings>() {}.type
        val file = File( "hueFileStorage")
        if (file.exists()) {
            val settings: UserSettings =
                gson.fromJson(ctx.openFileInput("hueFileStorage").bufferedReader(), userSettingsType)
            if (settings.authUser.isNotEmpty()) {
                return settings
            }
        }
        return UserSettings()
    }

    fun persistToFile (userSettings: UserSettings, ctx: Context){
        ctx.openFileOutput("hueFileStorage", Context.MODE_PRIVATE).bufferedWriter().write(gson.toJson(userSettings))
        Log.i("SCUP", "Persist Data")
        Log.i("SCUP", gson.toJson(userSettings))
    }
}