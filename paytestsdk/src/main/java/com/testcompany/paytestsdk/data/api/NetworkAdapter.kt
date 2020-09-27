package com.testcompany.paytestsdk.data.api

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.testcompany.paytestsdk.data.listener.NetworkCallback
import com.testcompany.paytestsdk.data.model.request.BaseRequest
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import java.io.File

internal class NetworkAdapter(context: Context) {

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

        requestQueue = RequestQueue(DiskBasedCache(cacheDir), network, 1)
        requestQueue.start()
    }

    fun <T: BaseResponse> sendRequest(baseRequest: BaseRequest, responseModel: Class<T>, callback: NetworkCallback<T>) {
        val request = GsonRequest(
            baseUrl,
            baseRequest,
            responseModel,
            headers,
            Response.Listener { response ->
                Log.d("TAG", "sendRequest: ${response.toString()}")
                callback.onSuccess(response)
            },
            Response.ErrorListener {
                // error handling
                Log.d("TAG", "sendRequest: ${it.toString()}")
                callback.onError(1, it?.localizedMessage)
            }
        )


        val volleyRequest = VolleyRequest(requestSpec, VolleyListener(callback))
        volleyRequest.setTag(REQUEST_TAG)
        volleyRequest.setShouldCache(false)
        requestQueue.add<Any>(volleyRequest)
    }

    fun cancelAllRequests() {
        requestQueue.cancelAll { request -> request.tag === REQUEST_TAG }
    }
}