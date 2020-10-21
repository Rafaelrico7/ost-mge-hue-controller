package com.example.hue.model

import com.google.gson.annotations.SerializedName

data class Light(
    @SerializedName("on")
    val on: Boolean = false,
    @SerializedName("sat")
    val saturation: Int = 254,
    @SerializedName("bri")
    val brightness: Int = 254,
    @SerializedName("hue")
    val hue: Int = 10000,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("effect")
    val effect: String = "",
    @SerializedName("xy")
    val xy: List<Double> = listOf(0.3171,0.3366),
    @SerializedName("ct")
    val ct: Int = 159,
    @SerializedName("alert")
    val alert: String = "none",
    @SerializedName("colormode")
    val colorMode: String = "xy",
    @SerializedName("mode")
    val mode: String = "",
    @SerializedName("reachable")
    val reachable: Boolean = true
)

data class LightList(
    val list: MutableList<Light>
)

data class Zone (
    @SerializedName("Name")
    val Name: String){
    @SerializedName("lights")
    val lights: MutableList<Light>? = null
    val isRoom: Boolean = false
}
