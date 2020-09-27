package com.testcompany.paytestsdk

import com.testcompany.paytestsdk.data.model.response.BaseResponse
import java.io.Serializable

interface PayTestListener : Serializable {
    fun <T : BaseResponse> onSuccess(response: T?)
    fun onError(error: String?, code: Int)
}