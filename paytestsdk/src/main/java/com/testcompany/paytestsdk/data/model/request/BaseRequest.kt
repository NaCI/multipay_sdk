package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.android.volley.Request
import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.annotations.SerializedName
import com.naci.pay_test_sdk_constants.Constants
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.data.model.RequestSpec
import kotlinx.android.parcel.Parcelize

const val PRIORITY_LOW = 0
const val PRIORITY_NORMAL = 1
const val PRIORITY_HIGH = 2
const val PRIORITY_IMMEDIATE = 3

@Parcelize
open class BaseRequest(
    @field:SerializedName("AppToken")
    var appToken: String = Constants.TOKEN,
    @field:SerializedName("LanguageCode")
    var languageCode: String = PayTest.language().code,
    var priority: Int = PRIORITY_NORMAL
) : Parcelable {

    open fun getPath(): String = ""

    private fun getHttpMethod() = Request.Method.POST

    fun createNetworkRequest(gson: Gson): RequestSpec? {
        return RequestSpec.Builder("/sdk/3.0" + this.getPath(), this.getHttpMethod()).setPriority(
            this.priority
        ).setBody(gson.toJson(this)).setTimeout(20000).build()
    }
}