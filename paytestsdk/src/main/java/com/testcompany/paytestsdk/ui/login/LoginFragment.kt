package com.testcompany.paytestsdk.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.R
import com.testcompany.paytestsdk.PayTestComponent
import com.testcompany.paytestsdk.PayTestListener
import com.testcompany.paytestsdk.base.BaseFragment
import com.testcompany.paytestsdk.data.error.PayTestError
import com.testcompany.paytestsdk.data.listener.NetworkCallback
import com.testcompany.paytestsdk.data.model.request.LoginGsm
import com.testcompany.paytestsdk.data.model.request.LoginInfoGsm
import com.testcompany.paytestsdk.data.model.response.BaseResponse
import com.testcompany.paytestsdk.data.model.response.Result
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

        PayTest.getComponent().requestManager()
            .loginRequest(loginRequestBody, object : NetworkCallback<Result> {
                override fun onSuccess(response: Result?) {
                    Toast.makeText(requireActivity(), response.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onError(error: PayTestError) {
                    Toast.makeText(requireActivity(), "FAIL : ${error.message}", Toast.LENGTH_LONG).show()
                }

            }, object : PayTestListener {
                override fun <T : BaseResponse> onSuccess(response: T?) {
                    listener.onSuccess(response)
                    requireActivity().finish()
                }

                override fun onError(error: String?, code: Int) {

                }

            })
    }

    override fun onDestroyView() {
        requireBinding().textInputEditEmailOrGsm.removeTextChangedListener(maskWatcher)
        super.onDestroyView()
    }
}