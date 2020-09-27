package com.testcompany.paytestsdk.data.listener

import com.testcompany.paytestsdk.data.error.PayTestError
import com.testcompany.paytestsdk.data.model.response.BaseResponse

interface NetworkCallback<T : BaseResponse> {
    fun onSuccess(response: T?)
    fun onError(error: PayTestError)
}