package com.example.hue.api

import android.content.Context
import android.util.Log
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class ApiClient {

    fun sendRequest(req: String, ipAddr: String, jsonBody: JSONObject, ctx: Context, method: Int, user: String, callback: ((String) -> Unit)?){
        Log.i("SCUP", "sendRequest")
        try {
            val requestQueue = Volley.newRequestQueue(ctx)
            val url = "https://$ipAddr/api$user$req"
            Log.i("SCUP", url)
            val requestBody = jsonBody.toString()
            Log.i("SCUP", requestBody)
            val stringRequest: StringRequest =
                object : StringRequest(method, url,
                    Response.Listener<String?> { response ->
                        if (response != null) {
                            Log.i("SCUP", response)
                            if (callback != null) {
                                callback(response)
                            }
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

                        responseString = response.statusCode.toString()
                        // can get more details such as response.headers

                        return Response.success(
                            responseString,
                            HttpHeaderParser.parseCacheHeaders(response)
                        )
                    }
                }
            stringRequest.retryPolicy =
                DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            requestQueue.add(stringRequest)
        } catch (e: JSONException) {
            e.printStackTrace()
        }



    }
}