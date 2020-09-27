package com.testcompany.paytestsdk.data.api

import com.testcompany.paytestsdk.data.listener.NetworkCallback
import com.testcompany.paytestsdk.data.model.request.BaseRequest
import com.testcompany.paytestsdk.data.model.response.BaseResponse

internal interface NetworkAdapter {
    fun <T : BaseResponse> sendRequest(
        baseUrl: String,
        baseRequest: BaseRequest,
        headers: MutableMap<String, String>,
        responseModel: Class<T>,
        callback: NetworkCallback<T>
    )

    fun cancelAllRequests()
}