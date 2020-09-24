package com.testcompany.sample

import android.app.Application
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.data.model.type.Language

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        PayTest.init(this,Language.TR)
    }
}