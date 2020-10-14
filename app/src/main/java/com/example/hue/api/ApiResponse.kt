package com.example.hue.api

import com.example.hue.model.Light
import com.google.gson.Gson

class ApiResponse (rsp: String){
    private val success: Light

    init {
        val gson = Gson()
        success = gson.fromJson(rsp, Light::class.java)
        if(success != null){
             println("> Item ${success}:\n")
        }
    }
}