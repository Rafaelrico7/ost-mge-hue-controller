package com.example.hue

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.hue.api.HttpsTrustManager
import com.example.hue.model.Memory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var addButtonClicked = false
    private var mem = Memory.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HttpsTrustManager.allowAllSSL()
        val ctx = this
        mem.setIpAddr("192.168.50.149", this)

        lightOn.setOnClickListener {
            GlobalScope.launch { mem.getUser(ctx) }.start()
            GlobalScope.launch(Dispatchers.IO) {
                mem.getLights(ctx) {
                    GlobalScope.launch(
                        Dispatchers.IO
                    ) { mem.setLightsStatus(true, ctx) }
                }
            }
        }
        lightOff.setOnClickListener {
            GlobalScope.launch { mem.getUser(ctx) }.start()
            GlobalScope.launch(Dispatchers.IO) {
                mem.getLights(ctx) {
                    GlobalScope.launch(
                        Dispatchers.IO
                    ) { mem.setLightsStatus(false, ctx) }.start()
                }
            }.start()
        }

        add_button.setOnClickListener {
            onAddButtonClicked()
        }

        menu_button.setOnClickListener {
            val myIntent = Intent(this, SettingsActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
            val toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
            val rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
            lightlist_button.startAnimation(toBottom)
            menu_button.startAnimation(toBottom)
            light_button.startAnimation(toBottom)
            add_button.startAnimation(rotateClose)

        }

        light_button.setOnClickListener {
            val myIntent = Intent(this, LightActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
            val toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
            val rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
            lightlist_button.startAnimation(toBottom)
            menu_button.startAnimation(toBottom)
            light_button.startAnimation(toBottom)
            add_button.startAnimation(rotateClose)
        }

        lightlist_button.setOnClickListener {
            val myIntent = Intent(this, LightListActivity::class.java)
            startActivity(myIntent)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
            val toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
            val rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
            lightlist_button.startAnimation(toBottom)
            menu_button.startAnimation(toBottom)
            light_button.startAnimation(toBottom)
            add_button.startAnimation(rotateClose)
        }
    }

    override fun onPause() {
        super.onPause()
        mem.persistSettings(this)
    }

    private fun onAddButtonClicked() {
        setVisibility(addButtonClicked)
        setAnimation(addButtonClicked)
        addButtonClicked = !addButtonClicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            menu_button.visibility = View.VISIBLE
            light_button.visibility = View.VISIBLE
        } else {
            menu_button.visibility = View.INVISIBLE
            light_button.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {
        val fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
        val toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
        val rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
        val rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)
        if (!clicked) {
            lightlist_button.startAnimation(fromBottom)
            menu_button.startAnimation(fromBottom)
            light_button.startAnimation(fromBottom)
            add_button.startAnimation(rotateOpen)
        } else {
            lightlist_button.startAnimation(toBottom)
            menu_button.startAnimation(toBottom)
            light_button.startAnimation(toBottom)
            add_button.startAnimation(rotateClose)
        }
    }
}
