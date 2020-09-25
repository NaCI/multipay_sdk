package com.testcompany.paytestsdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Response
import com.naci.pay_test_sdk_constants.Constants
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.R
import com.testcompany.paytestsdk.base.BaseFragment
import com.testcompany.paytestsdk.data.api.RequestManager
import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.request.LoginInfoGsm
import com.testcompany.paytestsdk.data.model.response.Login
import com.testcompany.paytestsdk.databinding.FragmentLoginBinding
import com.testcompany.paytestsdk.util.Formatter
import com.testcompany.paytestsdk.util.hideKeyboard
import com.testcompany.paytestsdk.view.listener.MaskWatcher


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private lateinit var maskWatcher: MaskWatcher

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
        initView()
        requireBinding().buttonLogin.setOnClickListener {
            buttonLoginClicked()
        }
    }

    private fun initView() {
        val maskPhone = getString(R.string.mask_phone)
        maskWatcher = MaskWatcher(requireBinding().textInputEditEmailOrGsm, maskPhone)
        requireBinding().textInputEditEmailOrGsm.addTextChangedListener(maskWatcher)
    }

    private fun buttonLoginClicked() {
        requireBinding().textInputEditEmailOrGsm.hideKeyboard()
        requireBinding().textInputEditPassword.hideKeyboard()

        val emailOrGsm = requireBinding().textInputEditEmailOrGsm.text.toString().trim()
        val password = requireBinding().textInputEditPassword.text.toString().trim()
        val url = "${Constants.BASE_URL}/MultiUService/SdkLogin"
        val headers = mutableMapOf<String, String>()
        headers["device-app-version"] = "4.4.5"
        val loginRequestBody =
            LoginGsm(LoginInfoGsm(Formatter.servicePhoneNumber(emailOrGsm), password))
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

    override fun onDestroyView() {
        requireBinding().textInputEditEmailOrGsm.removeTextChangedListener(maskWatcher)
        super.onDestroyView()
    }
}