package com.example.hue

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hue.model.Light
import com.example.hue.model.Memory
import kotlinx.android.synthetic.main.activity_lightlist.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LightListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lightlist)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mem = Memory.getInstance(this)
        val ctx = this
        /*mem.addLight(Light(0,false, 254,256,10000, "Bettlampe"))
        mem.addLight(Light(0,true, 254,256,10000, "Bettlampe 2"))
        mem.addLight(Light(0,false, 254,236,10000, "Lichtstreifen"))*/
        var viewAdapter : LightAdapter
        val viewManager = LinearLayoutManager(this)
        /*viewAdapter = LightAdapter(mem.getLocalLights())
        recViewLights.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }*/
        GlobalScope.launch {mem.getLights(ctx){ list -> viewAdapter = LightAdapter(list)
            recViewLights.apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }}.start()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true

            }

        }
        return super.onOptionsItemSelected(item)


    }
}