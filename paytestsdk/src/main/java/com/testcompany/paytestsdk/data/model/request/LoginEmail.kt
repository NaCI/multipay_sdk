package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import com.testcompany.paytestsdk.data.model.response.Login
import kotlinx.android.parcel.Parcelize
import kotlin.reflect.KClass

@Parcelize
internal data class LoginEmail(
    @field:SerializedName("loginInfo")
    var loginInfoEmail: LoginInfoEmail
) : BaseRequest(), Parcelable {
    override fun getPath(): String = "/SdkLogin"
}