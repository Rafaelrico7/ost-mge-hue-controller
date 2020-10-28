package com.example.hue.api

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.hue.model.Memory
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.json.JSONObject

class ApiClient {

    fun sendRequest(
        req: String,
        ipAddr: String,
        jsonBody: JSONObject,
        ctx: Context,
        method: Int,
        user: String,
        callback: ((res: JSONObject) -> Unit)?
    ) {
        Log.i("SCUP", "sendRequest")
        try {
            val requestQueue = Volley.newRequestQueue(ctx)
            val url = "https://$ipAddr/api$user$req"
            val jsonArray =
                Log.i("SCUP", url)
            Log.i("SCUP", jsonBody.toString())

            val jsonArrayRequest = MyJsonArrayRequest(method, url, jsonBody,
                { response ->
                    Log.i("SCUP", "JsonArrayRequest")
                    Log.i("SCUP", response.toString())
                },
                { error ->
                    val mem = Memory.getInstance(ctx)
                    runBlocking<Unit> {
                        val lamp = mem.getLight(
                            String(url.toByteArray()).takeLast(7).take(1).toInt() - 1,
                            ctx
                        )
                        Log.e("SCUP", "Fehler bei Request mit Lampe: ${lamp.name}", error)
                    }

                })
            val jsonObjRequest = JsonObjectRequest(method, url, jsonBody,
                { response ->
                    Log.i("SCUP", "JsonObjectRequest")
                    if (callback != null) {
                        callback(response)
                    }
                },
                { error ->
                    Log.e("SCUP", "Fehler bei Request", error)
                })
            jsonObjRequest.retryPolicy =
                DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            if (method == Request.Method.PUT) {

                Log.i("SCUP", jsonArrayRequest.body.decodeToString())
                requestQueue.add(jsonArrayRequest)
            } else {
                requestQueue.add(jsonObjRequest)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}