package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.testcompany.paytestsdk.PayTest
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseRequest(
    @field:SerializedName("AppToken")
    var appToken: String = "784fc69b7543455384a08beeb1d8c3c5",
    @field:SerializedName("LanguageCode")
    var languageCode: String = PayTest.language().code
) : Parcelable