package com.example.hue.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Light(
    var index: Int = 1,
    @SerializedName("on")
    var on: Boolean = false,
    @SerializedName("sat")
    var saturation: Int = 254,
    @SerializedName("bri")
    var brightness: Int = 254,
    @SerializedName("hue")
    var hue: Int = 10000,
    @SerializedName("name")
    var name: String? = "Lamp",
    @SerializedName("effect")
    var effect: String? = "none",
    @SerializedName("xy")
    var xy: List<Double> = listOf(0.3171,0.3366),
    @SerializedName("ct")
    var ct: Int = 159,
    @SerializedName("alert")
    var alert: String? = "none",
    @SerializedName("colormode")
    var colorMode: String? = "xy",
    @SerializedName("mode")
    var mode: String? = "none",
    @SerializedName("reachable")
    var reachable: Boolean = true
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
