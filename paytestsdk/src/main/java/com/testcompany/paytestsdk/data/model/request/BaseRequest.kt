package com.testcompany.paytestsdk.data.model.request

import com.google.gson.annotations.SerializedName
import com.testcompany.paytestsdk.BuildConfig
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.data.api.NetworkManager.Companion.PRIORITY_NORMAL

internal abstract class BaseRequest(
    @field:SerializedName("AppToken")
    var appToken: String = BuildConfig.API_TOKEN,
    @field:SerializedName("LanguageCode")
    var languageCode: String = PayTest.getComponent().language().code,
    var priority: Int = PRIORITY_NORMAL
) {
    open fun getPath(): String = ""

//    open fun <T: BaseResponse> getResponseClazz(): Class<T> = Result::class.java

    /*fun createNetworkRequest(gson: Gson): RequestSpec? {
        return RequestSpec.Builder("${NetworkManager.apiServicePath}${this.getPath()}").setPriority(
            this.priority
        ).setBody(gson.toJson(this)).setTimeout(DEFAULT_TIMEOUT_MILLIS).build()
    }*/
}