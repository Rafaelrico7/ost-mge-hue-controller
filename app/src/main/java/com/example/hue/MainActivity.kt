package com.example.hue

import android.os.Bundle
import android.widget.Toast
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
        val demoLight = Light(true, 254,256,10000, "Bettlampe")
        val demoLight2 = Light(false, 200,200,100, "Lightstrip")
        mem.addLight(demoLight)
        mem.addLight(demoLight2)
        val viewManager = LinearLayoutManager(this)
        val viewAdapter = LightAdapter(mem.getLights(this))

        allLights.setOnClickListener {

            recView.apply {
                layoutManager = viewManager
                adapter = viewAdapter

            }
        }
        button2.setOnClickListener {

            val user = mem.getUser(this)
            Toast.makeText(this, user,Toast.LENGTH_SHORT
            ).show()
        }
    }
}