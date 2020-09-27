package com.testcompany.paytestsdk

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.testcompany.paytestsdk.data.api.NetworkManager
import com.testcompany.paytestsdk.data.model.type.Language
import com.testcompany.paytestsdk.ui.login.LoginActivity
import com.testcompany.paytestsdk.util.getLanguage

internal class PayTestComponent(
    private val appContext: Context,
    private var language: Language?
) {

    init {
        this.language = getLanguage(appContext, language)
    }

    private val networkManager = NetworkManager(appContext)

    internal fun language() = requireNotNull(language)

    internal fun requireAppContext() = appContext

    internal fun setLanguage(language: Language?) {
        this.language = getLanguage(appContext, language)
    }

    internal fun gson() = Gson()

    internal fun startSDKForSubmitConsumer(context: Context, listener: PayTestListener) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.putExtra("listener", listener)
        context.startActivity(intent)
    }

    internal fun loginRequest(listener: PayTestListener) {
        networkManager.loginRequest(listener = listener)
    }
}