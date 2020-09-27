package com.testcompany.paytestsdk.data.model

import com.android.volley.Request
import com.google.gson.Gson
import com.google.gson.JsonIOException

internal class RequestSpec private constructor() {

    var baseUrl: String? = null
    var path: String? = null
        private set
    var httpMethod = Request.Method.POST
        private set
    var headers: MutableMap<String?, String?>? = null
        private set
    var body: String? = null
        private set
    var timeout = 0
        private set
    var priority = 0
        private set

    internal class Builder(private val path: String, private val httpMethod: Int = Request.Method.POST) {
        private var headers: Map<String?, String?>? = null
        private var body: String? = null
        private var timeout = 0
        private var priority = 0
        fun setHeaders(headers: Map<String?, String?>?): Builder {
            this.headers = headers
            return this
        }

        fun setBody(body: String?): Builder {
            this.body = body
            return this
        }

        fun setTimeout(timeout: Int): Builder {
            this.timeout = timeout
            return this
        }

        fun setPriority(priority: Int): Builder {
            this.priority = priority
            return this
        }

        fun build(): RequestSpec {
            val request = RequestSpec()
            request.path = path
            request.httpMethod = httpMethod
            request.headers = mutableMapOf()
            if (headers != null) {
                request.headers?.putAll(headers!!)
            }
            request.body = body
            request.timeout = timeout
            request.priority = priority
            return request
        }
    }
}