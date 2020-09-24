package com.testcompany.paytestsdk

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.testcompany.paytestsdk.data.model.type.Language
import com.testcompany.paytestsdk.util.getLanguage

object PayTest {

    private lateinit var requestQueue: RequestQueue
    private lateinit var language: Language

    @JvmStatic
    fun init(context: Context, language: Language? = null) {
        requestQueue = Volley.newRequestQueue(context.applicationContext)
        this.language = getLanguage(context, language)
    }

    @JvmStatic
    internal fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }

    @JvmStatic
    internal fun language() = language
}