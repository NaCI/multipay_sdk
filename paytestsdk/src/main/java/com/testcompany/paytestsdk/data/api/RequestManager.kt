package com.testcompany.paytestsdk.data.api

import com.testcompany.paytestsdk.PayTestListener
import com.testcompany.paytestsdk.data.listener.NetworkCallback
import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.response.Result

internal class RequestManager(private val networkManager: NetworkManager) {


    fun getNetworkManager() = networkManager

    fun loginRequest(
        loginGsm: LoginGsm,
        networkCallback: NetworkCallback<Result>,
        payTestListener: PayTestListener? = null
    ) {
        networkManager.sendRequest(
            loginGsm,
            Result::class.java,
            networkCallback,
            payTestListener
        )
    }
}