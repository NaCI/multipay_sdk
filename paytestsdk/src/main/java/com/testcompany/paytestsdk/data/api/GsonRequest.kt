package com.testcompany.paytestsdk.data.api

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.data.error.VolleyParseError
import com.testcompany.paytestsdk.data.model.request.BaseRequest
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

internal class GsonRequest<I : BaseRequest, O : BaseResponse>(
    url: String,
    private val requestClass: I,
    private val responseClass: Class<O>,
    private val headers: MutableMap<String, String>?,
    private val listener: Response.Listener<O>,
    errorListener: Response.ErrorListener
) : JsonRequest<O>(
    Method.POST,
    url,
    Gson().toJson(requestClass),
    listener,
    errorListener
) {

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: O) = listener.onResponse(response)

    override fun parseNetworkResponse(response: NetworkResponse?): Response<O> {
        return try {
            val json = String(
                response?.data ?: ByteArray(0),
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
//            val baseResponse = gson.fromJson(json, Result::class.java)
//            val model = gson.fromJson(baseResponse.result, responseClass)

            val model = PayTest.getComponent().gson().fromJson(json, responseClass)
            Response.success(
                model,
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(VolleyParseError(response))
        } catch (e: JsonSyntaxException) {
            Response.error(VolleyParseError(response))
        }
    }
}
