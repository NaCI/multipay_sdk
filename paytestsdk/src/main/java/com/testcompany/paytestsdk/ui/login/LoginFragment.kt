package com.testcompany.paytestsdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Response
import com.naci.pay_test_sdk_constants.Constants
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.base.BaseFragment
import com.testcompany.paytestsdk.data.api.RequestManager
import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.request.LoginInfoGsm
import com.testcompany.paytestsdk.data.model.response.Login
import com.testcompany.paytestsdk.databinding.FragmentLoginBinding


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
        val url = "${Constants.BASE_URL}/MultiUService/SdkLogin"
        val headers = mutableMapOf<String, String>()
        headers["device-app-version"] = "4.4.5"
        val loginRequestBody = LoginGsm(LoginInfoGsm("5335600090", "1234567a"))

        val loginRequest = RequestManager.GsonRequest<LoginGsm, Login>(
            url,
            loginRequestBody,
            Login::class.java,
            headers,
            Response.Listener<Login> { response ->
                Toast.makeText(requireActivity(), response.toString(), Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener {
                Toast.makeText(requireActivity(), "FAIL : ${it.message}", Toast.LENGTH_LONG).show()
            }
        )

        PayTest.addToRequestQueue(loginRequest)

    }
}