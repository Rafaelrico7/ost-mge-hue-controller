package com.example.hue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.colorpickerview.ActionMode
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.colorpicker_view.*
import kotlin.math.pow


class ColorPickerActivity : AppCompatActivity() {

    private var x : Float = 0.0f
    private var y : Float = 0.0f
    private var bri : Int = 0
    private var colorPickerView = ColorPickerView.Builder(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.colorpicker_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val extras = intent.extras
        if (extras != null){
            val hue = extras.get("hue") as Int
            bri = extras.get("brightness") as Int

        }

        colorPickerView.setColorListener(ColorEnvelopeListener { envelope, fromUser ->
            val colors = envelope.argb
            var red = (colors[0] / 255).toFloat()
            red = if (red > 0.04045f) (((red + 0.055f) / (1.0f + 0.055f)).pow(2.4f)) else red / 12.92f
            var green = (colors[1] / 255).toFloat()
            green = if (green > 0.04045f) (((green + 0.055f) / (1.0f + 0.055f)).pow(2.4f)) else green / 12.92f
            var blue = (colors[2] / 255).toFloat()
            blue = if (blue > 0.04045f) (((blue + 0.055f) / (1.0f + 0.055f)).pow(2.4f)) else blue / 12.92f
            x = red * 0.649926f + green * 0.103455f + blue * 0.197109f
            y = red * 0.234327f + green * 0.743075f + blue * 0.022598f
            val z : Float = red * 0.0000000f + green * 0.053077f + blue * 1.035763f
            x /= (x + y + z)
            y /= (x + y + z)
        })

        colorPickerView.setActionMode(ActionMode.LAST).setBrightnessSlideBar(brightnessSlideBar).build()
    }
}