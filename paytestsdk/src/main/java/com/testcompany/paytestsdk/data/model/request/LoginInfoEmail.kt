package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginInfoEmail(
    @field:SerializedName("Email")
    var email: String,
    @field:SerializedName("Password")
    var password: String
) : Parcelable