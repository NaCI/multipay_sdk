package com.testcompany.paytestsdk.data.api

import android.content.Context
import android.util.Log
import com.android.volley.NoConnectionError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.testcompany.paytestsdk.data.error.PayTestError
import com.testcompany.paytestsdk.data.error.VolleyCancelError
import com.testcompany.paytestsdk.data.error.VolleyParseError
import com.testcompany.paytestsdk.data.listener.NetworkCallback
import com.testcompany.paytestsdk.data.model.request.BaseRequest
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import java.io.File

internal class VolleyNetworkAdapter(context: Context) : NetworkAdapter {

    companion object {
        private const val REQUEST_TAG = "payTestRequestTag"
        private const val THREAD_POOL_SIZE = 1
    }

    private val requestQueue: RequestQueue

    init {
        val cacheDir = File(context.cacheDir, "volley")

        val network = BasicNetwork(HurlStack())
//        network = try {
//            Class.forName("okhttp3.OkHttpClient")
//            BasicNetwork(OkHttpStack())
//        } catch (e: ClassNotFoundException) {
//            BasicNetwork(HurlStack())
//        }

        requestQueue = RequestQueue(DiskBasedCache(cacheDir), network, THREAD_POOL_SIZE)
        requestQueue.start()
    }

    override fun <T : BaseResponse> sendRequest(
        baseUrl: String,
        baseRequest: BaseRequest,
        headers: MutableMap<String, String>,
        responseModel: Class<T>,
        callback: NetworkCallback<T>
    ) {
        val gsonRequest = GsonRequest(
            "$baseUrl${baseRequest.getPath()}",
            baseRequest,
            responseModel,
            headers,
            Response.Listener { response ->
                Log.d("VolleyNetworkAdapter", "sendRequest Response: $response")
                callback.onSuccess(response)
            },
            Response.ErrorListener { volleyError ->
                // error handling
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
                Log.d("VolleyNetworkAdapter", "sendRequest Error: $payTestError")
                callback.onError(payTestError)
            }
        )

        gsonRequest.headers.putAll(headers)
        gsonRequest.tag = REQUEST_TAG
        gsonRequest.setShouldCache(false)

        Log.d("VolleyNetworkAdapter", "sendRequest Start: $gsonRequest")
        requestQueue.add(gsonRequest)
    }

    override fun cancelAllRequests() {
        requestQueue.cancelAll { request -> request.tag === REQUEST_TAG }
    }
}