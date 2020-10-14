package com.example.hue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hue.model.Light
import com.example.hue.model.Memory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mem = Memory(this)
        val demoLight = Light(false, 254,256,10000, "Bettlampe")
        mem.addLight(demoLight)
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = LightAdapter(mem.getLights())

        allLights.setOnClickListener {

            recView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter

            }
        }
    }
}