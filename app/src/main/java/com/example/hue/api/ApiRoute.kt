package com.example.hue.api

import com.android.volley.Request
import org.json.JSONObject

class ApiRoute
{
    fun eval(route: SApiRoute) {
        val apiClient = ApiClient()
        when (route) {
            is GetUser -> apiClient.sendRequest(
                "",
                route.ipAdress,
                JSONObject().put("devicetype", route.devicetype),
                route.ctx,
                Request.Method.POST,
                "",
                route.callback
            )
            is GetLights -> apiClient.sendRequest(
                "/lights",
                route.ipAdress,
                JSONObject(),
                route.ctx,
                Request.Method.GET,
                "/${route.authUser}",
                route.callback
            )
            is GetLight -> apiClient.sendRequest(
                "/lights/${route.lightIdx}",
                route.ipAdress,
                JSONObject(),
                route.ctx,
                Request.Method.GET,
                "/${route.authUser}",
                route.callback
            )
            is SetLightStatus -> apiClient.sendRequest(
                "/lights/${route.lightIdx}/state", route.ipAdress,
                JSONObject().put("on", route.status.on)
                    .put("sat", route.status.saturation)
                    .put("bri", route.status.brightness)
                    .put("hue", route.status.hue)
                    .put("name", route.status.name),
                route.ctx,
                Request.Method.PUT,
                "/${route.authUser}",
                null
            )
        }
    }

    }