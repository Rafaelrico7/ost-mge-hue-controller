package com.example.hue.api

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest

import com.android.volley.toolbox.Volley
import org.json.JSONArray
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
    ){
        Log.i("SCUP", "sendRequest")
        try {
            val requestQueue = Volley.newRequestQueue(ctx)
            val url = "https://$ipAddr/api$user$req"
            val jsonArray = JSONArray()
            Log.i("SCUP", url)
            Log.i("SCUP", jsonBody.toString())

            val jsonArrayRequest = JsonArrayRequest(method, url, jsonArray.put(jsonBody.toString()),
                { _ ->
                    Log.i("SCUP", "JsonArrayRequest")
                },
                { error -> Log.e("SCUP", "Fehler bei Request", error) })
            val jsonObjRequest = JsonObjectRequest(method, url, jsonBody,
                { response ->
                    Log.i("SCUP", "JsonObjectRequest")
                    if (callback != null) {
                        callback(response)
                    }
                },
                { error -> Log.e("SCUP", "Fehler bei Request", error) })
            jsonObjRequest.retryPolicy =
                DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
            if (method == Request.Method.PUT){
                requestQueue.add(jsonArrayRequest)
            }else{
                requestQueue.add(jsonObjRequest)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }



    }
}