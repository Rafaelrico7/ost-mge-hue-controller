package com.example.hue

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hue.model.Light
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.light_view.*
import kotlinx.android.synthetic.main.light_view.view.*
import kotlin.math.pow

class LightAdapter(private val lightList: List<Light>) : RecyclerView.Adapter<LightAdapter.LightViewHolder>() {

    class LightViewHolder(val lightLayout: ConstraintLayout) : RecyclerView.ViewHolder(lightLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val lightLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.light_view, parent, false) as ConstraintLayout
        return LightViewHolder(lightLayout)
    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        val light = lightList[position]
        holder.lightLayout.ligth_view_text.text = light.name
        if ((holder.lightLayout.light_view_switch.isChecked && !light.on)
            or (!holder.lightLayout.light_view_switch.isChecked && light.on)
        ) {
            holder.lightLayout.light_view_switch.toggle()
        }

        holder.lightLayout.light_view_btn.setOnClickListener {
            val cPiDi = ColorPickerDialog.Builder(holder.lightLayout.context)
            cPiDi.setTitle("Pick a Colour")
            cPiDi.setPositiveButton(R.string.submitButton,
                ColorEnvelopeListener { envelope, fromUser ->
                val colors = envelope.argb
                var red = (colors[0] / 255).toFloat()
                red = if (red > 0.04045f) (((red + 0.055f) / (1.0f + 0.055f)).pow(2.4f)) else red / 12.92f
                var green = (colors[1] / 255).toFloat()
                green = if (green > 0.04045f) (((green + 0.055f) / (1.0f + 0.055f)).pow(2.4f)) else green / 12.92f
                var blue = (colors[2] / 255).toFloat()
                blue = if (blue > 0.04045f) (((blue + 0.055f) / (1.0f + 0.055f)).pow(2.4f)) else blue / 12.92f
                var x = red * 0.649926f + green * 0.103455f + blue * 0.197109f
                var y = red * 0.234327f + green * 0.743075f + blue * 0.022598f
                val z : Float = red * 0.0000000f + green * 0.053077f + blue * 1.035763f
                x /= (x + y + z)
                y /= (x + y + z)
                    Log.i("SCUP", "x:$x ,y:$y")

            })
            cPiDi.setNegativeButton(R.string.cancelButton, DialogInterface.OnClickListener() { dialogInterface, _ ->
                dialogInterface.dismiss()
            }).attachAlphaSlideBar(false).setBottomSpace(12).show()
        }
    }
    override fun getItemCount()= lightList.size
}

