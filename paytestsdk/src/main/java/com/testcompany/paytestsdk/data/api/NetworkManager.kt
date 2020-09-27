package com.testcompany.paytestsdk.data.api

import android.content.Context
import android.util.Log
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.PayTestListener
import com.testcompany.paytestsdk.data.listener.NetworkCallback
import com.testcompany.paytestsdk.data.model.RequestSpec
import com.testcompany.paytestsdk.data.model.request.BaseRequest
import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.request.LoginInfoGsm
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import com.testcompany.paytestsdk.data.model.response.Login

internal class NetworkManager(context: Context) {
    private val HEADER_KEY_OPY_ID = "opy-id"
    private val HEADER_KEY_SESSION_ID = "session-id"
    private val HEADER_KEY_DEVICE_INFO = "device-info-model"
    private val HEADER_KEY_OS_INFO = "device-os-info"
    private val HEADER_KEY_OS_VERSION = "device-os-version"
    private val HEADER_KEY_APP_VERSION = "device-app-version"

    private val baseUrl = "https://test-multinet-multipay-api.inventiv.services"

    companion object {
        internal const val apiServicePath = "/MultiUService"
        internal const val DEFAULT_TIMEOUT_MILLIS = 20000
        const val PRIORITY_LOW = 0
        const val PRIORITY_NORMAL = 1
        const val PRIORITY_HIGH = 2
        const val PRIORITY_IMMEDIATE = 3
    }

    val requestQueue: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    fun sendRequest(baseRequest: BaseRequest) {

        val requestSpec = RequestSpec.Builder("", 1)
            .setBody("asd")
            .build()
        requestSpec.baseUrl = "asd"
    }

    fun loginRequest(username: String = "", password: String = "", listener: PayTestListener) {

        val loginRequestBody = LoginGsm(LoginInfoGsm("5335600090", "1234567a"))
        /*val loginRequest = GsonRequest<LoginGsm, Login>(
            baseUrl,
            loginRequestBody,
            Login::class.java,
            headers,
            Response.Listener<Login> { response ->
                Toast.makeText(requireActivity(), response.toString(), Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                Toast.makeText(requireActivity(), "FAIL : ${it.message}", Toast.LENGTH_LONG).show()
            }
        )

        requestQueue.add(loginRequest)*/

        loginRequestBody.createNetworkRequest(PayTest.getComponent().gson())

        sendRequest(loginRequestBody, Login::class.java, object : NetworkCallback<Login> {
            override fun onSuccess(response: Login) {
                listener.onSuccess(response.sessionToken!!)
            }

            override fun onError(errorCode: Int, errorMessage: String?) {
                listener.onError(errorMessage, errorCode)
            }

        })
    }

    fun <T : BaseResponse> sendRequest(
        baseRequest: BaseRequest,
        responseModel: Class<T>,
        callback: NetworkCallback<T>
    ) {
        val headers = mutableMapOf<String, String>()

        // add headers
        /*val request = GsonRequest(
            baseUrl,
            baseRequest,
            headers,
            Response.Listener { response ->
                Gson().fromJson(response, clazz)
            },
            Response.ErrorListener {
//                Toast.makeText(requireActivity(), "FAIL : ${it.message}", Toast.LENGTH_LONG).show()
            }
        )*/

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

        requestQueue.add(request)
    }
}