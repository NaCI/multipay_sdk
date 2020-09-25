package com.testcompany.paytestsdk.data.api

import com.testcompany.paytestsdk.data.model.RequestSpec
import com.testcompany.paytestsdk.data.model.request.BaseRequest

class NetworkManager {
    private val KEY_OPY_ID = "opy-id"
    private val KEY_SESSION_ID = "session-id"
    private val KEY_DEVICE_INFO = "device-info-model"
    private val KEY_OS_INFO = "device-os-info"
    private val KEY_OS_VERSION = "device-os-version"
    private val KEY_APP_VERSION = "device-app-version"

    private val baseUrl = "https://test-multinet-multipay-api.inventiv.services"

    fun sendRequest(baseRequest: BaseRequest) {

        val requestSpec = RequestSpec.Builder("", 1)
            .setBody("asd")
            .build()
        requestSpec.baseUrl = "asd"
    }
}