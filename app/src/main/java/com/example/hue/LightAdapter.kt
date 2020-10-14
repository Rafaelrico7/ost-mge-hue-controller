package com.example.hue

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hue.model.Light
import kotlinx.android.synthetic.main.light_view.view.*

class LightAdapter(private val lightList: List<Light>) : RecyclerView.Adapter<LightAdapter.LightViewHolder>() {

    class LightViewHolder(val lightLayout: ConstraintLayout) : RecyclerView.ViewHolder(lightLayout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LightViewHolder {
        val lightLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.light_view, parent, false) as ConstraintLayout
        return LightViewHolder(lightLayout)
    }

    override fun onBindViewHolder(holder: LightViewHolder, position: Int) {
        lightList.forEach { light ->
            holder.lightLayout.ligth_view_text.text = light.name
            if(    (holder.lightLayout.light_view_switch.isChecked && !light.on)
                or (!holder.lightLayout.light_view_switch.isChecked && light.on)
            ){
                holder.lightLayout.light_view_switch.toggle()
            }
        }

    }

    override fun getItemCount()= lightList.size
}