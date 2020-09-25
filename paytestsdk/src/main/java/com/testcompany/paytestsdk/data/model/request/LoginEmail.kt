package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginEmail(
    @field:SerializedName("loginInfo")
    var loginInfoEmail: LoginInfoEmail
) : BaseRequest(), Parcelable {
    override fun getPath(): String = "/MultiUService/SdkLogin"
}