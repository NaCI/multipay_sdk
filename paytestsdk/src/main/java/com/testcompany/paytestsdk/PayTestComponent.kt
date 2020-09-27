package com.testcompany.paytestsdk

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.testcompany.paytestsdk.data.api.NetworkAdapter
import com.testcompany.paytestsdk.data.api.NetworkManager
import com.testcompany.paytestsdk.data.api.RequestManager
import com.testcompany.paytestsdk.data.api.VolleyNetworkAdapter
import com.testcompany.paytestsdk.data.model.type.Language
import com.testcompany.paytestsdk.ui.login.LoginActivity
import com.testcompany.paytestsdk.util.getLanguage

internal class PayTestComponent(
    private val appContext: Context,
    private var language: Language?
) {

    private val networkManager: NetworkManager
    private val networkAdapter: NetworkAdapter
    private val requestManager: RequestManager

    init {
        this.language = getLanguage(appContext, language)
        networkAdapter = VolleyNetworkAdapter(appContext)
        networkManager = NetworkManager(networkAdapter)
        requestManager = RequestManager(networkManager)
    }

    internal fun requestManager() = requestManager

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
}