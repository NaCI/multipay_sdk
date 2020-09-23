package com.inventiv.multipaysdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginEmail(
    @field:SerializedName("loginInfo")
    var loginInfoEmail: LoginInfoEmail
) : BaseRequest(), Parcelable