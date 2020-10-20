package com.example.hue.api

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
                "",
                route.callback
            )
            is GetLights -> apiClient.sendRequest(
                "/lights",
                route.ipAdress,
                JSONObject(),
                route.ctx,
                route.authUser,
                route.callback
            )
            is GetLight -> apiClient.sendRequest(
                "/lights/${route.light}",
                route.ipAdress,
                JSONObject(),
                route.ctx,
                "",
                route.callback
            )
            is SetLightStatus -> apiClient.sendRequest(
                "", route.ipAdress,
                JSONObject().put("on", route.status.on)
                    .put("sat", route.status.saturation)
                    .put("bri", route.status.brightness)
                    .put("hue", route.status.hue),
                route.ctx, "", route.callback
            )
        }
    }

    }