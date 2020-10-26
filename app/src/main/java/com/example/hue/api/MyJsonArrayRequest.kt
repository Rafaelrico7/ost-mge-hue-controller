package com.example.hue.api

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


class MyJsonArrayRequest
/**
 * Creates a new request.
 *
 * @param method the HTTP method to use
 * @param url URL to fetch the JSON from
 * @param jsonRequest A [JSONArray] to post with the request. Null indicates no parameters
 * will be posted along with request.
 * @param listener Listener to receive the JSON response
 * @param errorListener Error listener, or null to ignore errors.
 */(
    method: Int,
    url: String?,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject?>?,
    errorListener: Response.ErrorListener?
) : JsonRequest<JSONObject>(
    method,
    url,
    jsonRequest?.toString(),
    listener,
    errorListener
) {


    override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
        return try {
            val jsonString = String(
                response!!.data,
                Charset.defaultCharset()
            )
            Response.success<JSONObject>(
                JSONObject("{JSONArray(jsonString).toString()}"), HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error<JSONObject>(ParseError(e))
        } catch (je: JSONException) {
            Response.error<JSONObject>(ParseError(je))
        }
    }
}