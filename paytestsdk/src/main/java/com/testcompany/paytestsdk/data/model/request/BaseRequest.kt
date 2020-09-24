package com.testcompany.paytestsdk.data.model.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.naci.pay_test_sdk_constants.Constants
import com.testcompany.paytestsdk.PayTest
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseRequest(
    @field:SerializedName("AppToken")
    var appToken: String = Constants.TOKEN,
    @field:SerializedName("LanguageCode")
    var languageCode: String = PayTest.language().code
) : Parcelable