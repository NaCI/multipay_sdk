package com.testcompany.paytestsdk.data.model.request

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.naci.pay_test_sdk_constants.Constants
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.data.api.NetworkManager
import com.testcompany.paytestsdk.data.api.NetworkManager.Companion.DEFAULT_TIMEOUT_MILLIS
import com.testcompany.paytestsdk.data.api.NetworkManager.Companion.PRIORITY_NORMAL
import com.testcompany.paytestsdk.data.model.RequestSpec
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import com.testcompany.paytestsdk.data.model.response.Login
import kotlin.reflect.KClass

internal abstract class BaseRequest(
    @field:SerializedName("AppToken")
    var appToken: String = Constants.TOKEN,
    @field:SerializedName("LanguageCode")
    var languageCode: String = PayTest.getComponent().language().code,
    var priority: Int = PRIORITY_NORMAL
) {
    open fun getPath(): String = ""

    open fun <T: BaseResponse> getResponseClazz(): Class<T> = BaseResponse::class.java as Class<T>

    fun createNetworkRequest(gson: Gson): RequestSpec? {
        return RequestSpec.Builder("${NetworkManager.apiServicePath}${this.getPath()}").setPriority(
            this.priority
        ).setBody(gson.toJson(this)).setTimeout(DEFAULT_TIMEOUT_MILLIS).build()
    }
}