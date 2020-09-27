package com.testcompany.paytestsdk.data.listener

import com.android.volley.NoConnectionError
import com.android.volley.Response
import com.android.volley.VolleyError
import com.testcompany.paytestsdk.data.error.PayTestError
import com.testcompany.paytestsdk.data.error.VolleyCancelError
import com.testcompany.paytestsdk.data.error.VolleyParseError
import com.testcompany.paytestsdk.data.model.response.BaseResponse


internal class VolleyListener<T : BaseResponse>(private val callback: NetworkCallback<T>) :
    Response.Listener<T?>, Response.ErrorListener {

    override fun onResponse(responseBody: T?) {
        callback.onSuccess(responseBody)
    }

    override fun onErrorResponse(volleyError: VolleyError?) {
        try {
            val payTestError: PayTestError = when {
                volleyError == null -> {
                    PayTestError.generalInstance()
                }
                volleyError is VolleyCancelError -> {
                    PayTestError.cancelInstance()
                }
                volleyError is NoConnectionError -> {
                    PayTestError.noConnectionInstance()
                }
                volleyError is VolleyParseError -> {
                    PayTestError.invalidResponseInstance()
                }
                volleyError.networkResponse != null -> {
                    PayTestError.serverErrorInstance(
                        volleyError.message ?: "",
                        volleyError.networkResponse.statusCode,
                        volleyError.networkResponse.data
                    )
                }
                else -> {
                    if (volleyError.message.isNullOrEmpty()) {
                        PayTestError.generalInstance()
                    } else {
                        PayTestError.generalInstance(volleyError.message!!)
                    }
                }
            }
            callback.onError(payTestError.statusCode, payTestError.message)
        } catch (e: Exception) {
            // TODO : add logger
//            Netmera.logger().e(e, "Network error was catch successfully", arrayOfNulls<Any>(0))
        }
    }

}
