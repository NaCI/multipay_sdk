package com.testcompany.paytestsdk.data.listener

import com.testcompany.paytestsdk.data.model.response.ResponseModel

interface NetworkCallback<T: ResponseModel> {
    fun onSuccess(reponse: T)
    fun onError(errorCode: Int, errorMessage: String?)
}