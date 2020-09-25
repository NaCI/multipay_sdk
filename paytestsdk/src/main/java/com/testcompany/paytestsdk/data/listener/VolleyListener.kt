package com.testcompany.paytestsdk.data.listener

import com.android.volley.NoConnectionError
import com.android.volley.Response
import com.android.volley.VolleyError


/*
internal class VolleyListener(callback: NetworkCallback) :
    Response.Listener<String?>, Response.ErrorListener {
    private val callback: NetworkCallback
    override fun onResponse(responseBody: String?) {
        callback.onResponse(responseBody, null as NetmeraError?)
    }

    override fun onErrorResponse(volleyError: VolleyError) {
        try {

            callback.onResponse(null as String?, netmeraError)
        } catch (var3: Exception) {
            Netmera.logger().e(var3, "Network error was catched successfully", arrayOfNulls<Any>(0))
        }
    }

    init {
        this.callback = callback
    }
}*/
