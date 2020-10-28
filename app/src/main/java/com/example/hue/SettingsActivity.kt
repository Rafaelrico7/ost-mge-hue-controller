package com.example.hue

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hue.model.Memory
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        button.setOnClickListener{
            getBridgeIP(it)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)




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

    fun getBridgeIP(view: View){
        val text = findViewById<EditText>(R.id.editText)
        val ip = editText.text.toString()
        val mem = Memory.getInstance(this)
        mem.setIpAddr(ip, this)
        Toast.makeText(this, "IP was successfully set!", Toast.LENGTH_SHORT).show()


    }

}