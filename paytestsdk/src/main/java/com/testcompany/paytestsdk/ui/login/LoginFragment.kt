package com.testcompany.paytestsdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.base.BaseFragment
import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.request.LoginInfoGsm
import com.testcompany.paytestsdk.databinding.FragmentLoginBinding
import java.io.UnsupportedEncodingException


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment().apply {
            val args = Bundle().apply {
            }
            arguments = args
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "https://test-multinet-multipay-api.inventiv.services/MultiUService/SdkLogin"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                Toast.makeText(requireActivity(), response, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                Toast.makeText(requireActivity(), "FAIL", Toast.LENGTH_LONG).show()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                return super.getHeaders()
            }

            override fun getBody(): ByteArray {
                val login = LoginGsm(LoginInfoGsm("5335600090", "1234567a"))
                val requestBody = Gson().toJson(login, LoginGsm::class.java)
                try {
                    return requestBody.toByteArray()
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf(
                        "Unsupported Encoding while trying to get the bytes of %s using %s",
                        requestBody,
                        "utf-8"
                    )
                    throw IllegalArgumentException("Erim")
                }
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        PayTest.addToRequestQueue(stringRequest)

    }
}