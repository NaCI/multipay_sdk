package com.testcompany.paytestsdk.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login (
    @field:SerializedName("OpyId")
    var opyId: String?,

    @field:SerializedName("Name")
    var name: String?,

    @field:SerializedName("Surname")
    var surname: String?,

    @field:SerializedName("Email")
    var email: String?,

    @field:SerializedName("Gsm")
    var gsm: String?,

    @field:SerializedName("UserMessage")
    var userMessage: String?,

    @field:SerializedName("PinCodeProtected")
    var pinCodeProtected: Boolean?,

    @field:SerializedName("PinCodeThresholdAmount")
    var pinCodeThresholdAmount: Money?,

    @field:SerializedName("SessionToken")
    var sessionToken: String?
) : BaseResponse, Parcelable