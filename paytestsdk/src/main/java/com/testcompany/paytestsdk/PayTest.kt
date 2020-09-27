package com.testcompany.paytestsdk

import android.content.Context
import com.testcompany.paytestsdk.data.model.type.Language

object PayTest {
    private lateinit var payTestComponent: PayTestComponent

    @JvmOverloads
    @JvmStatic
    fun init(context: Context, language: Language? = null) {
        this.payTestComponent = PayTestComponent(context, language)
    }

    @JvmStatic
    fun setLanguage(language: Language) {
        payTestComponent.setLanguage(language)
    }

    internal fun getComponent() = payTestComponent

    @JvmStatic
    fun startSDKForSubmitConsumer(context: Context, listener: PayTestListener) {
        getComponent().startSDKForSubmitConsumer(context, listener)
    }
}