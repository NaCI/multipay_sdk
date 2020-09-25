package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import com.testcompany.paytestsdk.data.model.response.Login
import com.testcompany.paytestsdk.data.model.response.ResponseModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginGsm(
    @field:SerializedName("loginInfo")
    var loginInfoGsm: LoginInfoGsm
) : BaseRequest(), Parcelable {
    override fun getPath() = "/MultiUService/SdkLogin"
}