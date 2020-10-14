package com.example.hue.api

import org.json.JSONObject

class ApiRoute
{
    fun eval(route: SApiRoute){
        val apiClient = ApiClient()
        when(route) {
            is GetUser -> apiClient.sendRequest("", route.ipAdress, JSONObject().put("devicetype", route.devicetype), route.ctx, "", route.callback)
            is GetLights -> apiClient.sendRequest("", route.ipAdress, JSONObject().put("", ""), route.ctx, "", route.callback)
            is GetLight -> apiClient.sendRequest("", route.ipAdress, JSONObject().put("devicetype", ""), route.ctx, "", route.callback)
            is SetLightStatus -> apiClient.sendRequest("", route.ipAdress, JSONObject().put("devicetype", ""), route.ctx, "", route.callback)
        }
    }

    }