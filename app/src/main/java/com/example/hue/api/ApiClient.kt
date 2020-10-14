package com.example.hue.api

import android.content.Context
import android.util.Log
import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class ApiClient {

    fun sendRequest(req: String, ipAddr: String, jsonBody: JSONObject, ctx: Context, user: String, callback: (String)->String){

        try {
            val requestQueue = Volley.newRequestQueue(ctx)
            val url = "http://$ipAddr/api$user$req"
            val requestBody = jsonBody.toString()
            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, url,
                    Response.Listener<String?> { response ->
                        if (response != null) {
                            Log.i("VOLLEY", response)
                            callback(response)
                        }
                    },
                    Response.ErrorListener { error -> Log.e("VOLLEY", error.toString()) }) {
                    override fun getBodyContentType(): String {
                        return "application/json; charset=utf-8"
                    }

                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray? {
                        return try {
                            requestBody.toByteArray(charset("utf-8"))
                        } catch (uee: UnsupportedEncodingException) {
                            VolleyLog.wtf(
                                "Unsupported Encoding while trying to get the bytes of %s using %s",
                                requestBody,
                                "utf-8"
                            )
                            return null

                        }
                    }

                    override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                        var responseString = ""
                        if (response != null) {
                            responseString = response.statusCode.toString()
                            // can get more details such as response.headers
                        }
                        return Response.success(
                            responseString,
                            HttpHeaderParser.parseCacheHeaders(response)
                        )
                    }
                }
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }



    }
}