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
    val name: String = ""
)

data class Zone (
    @SerializedName("Name")
    val Name: String){
    @SerializedName("lights")
    val lights: MutableList<Light>? = null
}
data class Room (
    @SerializedName("Name")
    val Name: String){
    @SerializedName("zones")
    val zones: MutableList<Zone>? = null
}