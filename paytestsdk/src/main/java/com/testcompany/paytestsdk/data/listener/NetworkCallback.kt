package com.testcompany.paytestsdk.data.listener

import com.testcompany.paytestsdk.data.model.response.BaseResponse

interface NetworkCallback<T: BaseResponse> {
    fun onSuccess(response: T?)
    fun onError(errorCode: Int, errorMessage: String?)
}