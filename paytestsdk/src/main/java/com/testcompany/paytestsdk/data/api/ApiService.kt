package com.testcompany.paytestsdk.data.api

import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.response.BaseResponse

interface ApiService {

    fun loginGsm(loginGsm: LoginGsm?): BaseResponse?
}