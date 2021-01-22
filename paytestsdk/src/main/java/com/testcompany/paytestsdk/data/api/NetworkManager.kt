package com.testcompany.paytestsdk.data.api

import com.testcompany.paytestsdk.BuildConfig
import com.testcompany.paytestsdk.PayTestListener
import com.testcompany.paytestsdk.data.error.PayTestError
import com.testcompany.paytestsdk.data.listener.NetworkCallback
import com.testcompany.paytestsdk.data.model.request.BaseRequest
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import com.testcompany.paytestsdk.data.model.response.Result

internal class NetworkManager(private val networkAdapter: VolleyNetworkAdapter) {
    private val HEADER_KEY_OPY_ID = "opy-id"
    private val HEADER_KEY_SESSION_ID = "session-id"
    private val HEADER_KEY_DEVICE_INFO = "device-info-model"
    private val HEADER_KEY_OS_INFO = "device-os-info"
    private val HEADER_KEY_OS_VERSION = "device-os-version"
    private val HEADER_KEY_APP_VERSION = "device-app-version"

    private val baseUrl = BuildConfig.END_POINT

    companion object {
        private const val TAG = "NetworkManager"
        internal const val apiServicePath = BuildConfig.API_SERVICE
        internal const val DEFAULT_TIMEOUT_MILLIS = 20000
        const val PRIORITY_LOW = 0
        const val PRIORITY_NORMAL = 1
        const val PRIORITY_HIGH = 2
        const val PRIORITY_IMMEDIATE = 3
    }

    fun getNetworkAdapter() = networkAdapter

    fun <T : BaseResponse> sendRequest(
        baseRequest: BaseRequest,
        responseModel: Class<T>,
        responseCallback: NetworkCallback<T>,
        payTestListener: PayTestListener? = null
    ) {
        val headers = mutableMapOf<String, String>()
        // TODO : add headers here or anything that needs to be intercepted to request
        headers[HEADER_KEY_OS_VERSION] = "Android"

        networkAdapter.sendRequest(
            "$baseUrl$apiServicePath",
            baseRequest,
            headers,
            responseModel,
            object : NetworkCallback<T> {
                override fun onSuccess(response: T?) {
                    handleNetworkResponse(response, null, responseCallback, payTestListener)
                }

                override fun onError(error: PayTestError) {
                    handleNetworkResponse(null, error, responseCallback, payTestListener)
                }

            }
        )
    }

    private fun <T : BaseResponse> handleNetworkResponse(
        response: T?,
        payTestError: PayTestError?,
        callback: NetworkCallback<T>,
        listener: PayTestListener?
    ) {
        payTestError?.let {
            deliverResponse(null, payTestError, callback, listener)
            return
        }

        if (response !is Result) {
            deliverResponse(response, null, callback, listener)
            return
        }

        val resultCode: Int = response.resultCode
        val payTestError =
            PayTestError.payTestErrorInstance(response.resultMessage, response.resultCode, response)

        //TODO : hata durumları burada handle edilecek
        when (resultCode) {
            21494 -> {
                deliverResponse(response, payTestError, callback, listener)
                return
            }
            in 21401..21450 -> {
                //            show(result.getResultMessage())
            }
            21451 -> {
                //            LoginActivity.start(context)
            }
            21455 -> {
                //            show(result.getResultMessage())
            }
            21499 -> {
                //            val message: String =
                //                result.getResultMessage() + DELIMITER_DEVICE_INFO_WITH_SPACE + resultCode
                //            show(message)
            }
            in 21461..21500 -> {
                //            val errorSystemWithCode: String = context.getString(R.string.Error_System_WithCode)
                //            val message = String.format(errorSystemWithCode, resultCode)
                //            show(message)
            }
            99999 -> {
                //            show(context.getString(R.string.Error_System))
            }
            20001 -> {
                //            val errorSystemWithCode: String = context.getString(R.string.Error_System_WithCode)
                //            val message = String.format(errorSystemWithCode, resultCode)
                //            show(message)
            }
            20002 -> {
                //            val errorSystemWithCode: String = context.getString(R.string.Error_System_WithCode)
                //            val message = String.format(errorSystemWithCode, resultCode)
                //            show(message)
            }
            20202 -> {
                //            show(result.getResultMessage())
            }
        }

        if (response.isSuccess()) {
            deliverResponse(response, null, callback, listener)
        } else {
            deliverResponse(null, payTestError, callback, listener)
        }
    }

    private fun <T : BaseResponse> deliverResponse(
        response: T?,
        payTestError: PayTestError?,
        callback: NetworkCallback<T>,
        listener: PayTestListener?
    ) {
        payTestError?.let {
            callback.onError(it)
            listener?.let { payTestListener ->
                payTestListener.onError(payTestError.message, payTestError.errorCode)
            }
            return
        }

        callback.onSuccess(response)
        listener?.let {
            it.onSuccess(response)
        }
    }
}