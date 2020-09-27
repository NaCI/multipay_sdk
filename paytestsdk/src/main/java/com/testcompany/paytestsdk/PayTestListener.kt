package com.testcompany.paytestsdk

import java.io.Serializable

interface PayTestListener: Serializable {
    fun onSuccess(token: String)
    fun onError(error: String?, code: Int)
}