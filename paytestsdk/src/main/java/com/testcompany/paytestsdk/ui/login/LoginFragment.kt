package com.testcompany.paytestsdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naci.pay_test_sdk_constants.Constants
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.R
import com.testcompany.paytestsdk.PayTestComponent
import com.testcompany.paytestsdk.PayTestListener
import com.testcompany.paytestsdk.base.BaseFragment
import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.request.LoginInfoGsm
import com.testcompany.paytestsdk.data.repository.AuthenticationRepository
import com.testcompany.paytestsdk.databinding.FragmentLoginBinding
import com.testcompany.paytestsdk.util.Formatter
import com.testcompany.paytestsdk.util.hideKeyboard
import com.testcompany.paytestsdk.view.listener.MaskWatcher


internal class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    lateinit var listener: PayTestListener

    private lateinit var maskWatcher: MaskWatcher

    companion object {
        fun newInstance(listener: PayTestListener): LoginFragment = LoginFragment().apply {
            val args = Bundle().apply {
                putSerializable("listener", listener)
            }
            arguments = args
        }
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener = arguments?.getSerializable("listener") as PayTestListener
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
            baseUrl,
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