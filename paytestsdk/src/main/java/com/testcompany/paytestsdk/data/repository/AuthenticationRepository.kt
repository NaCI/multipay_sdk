package com.testcompany.paytestsdk.data.repository

import com.google.gson.Gson
import com.testcompany.paytestsdk.data.model.request.*
import com.testcompany.paytestsdk.data.model.type.ValidationErrorType
import com.testcompany.paytestsdk.util.Formatter
import com.testcompany.paytestsdk.util.Validator

class AuthenticationRepository {
    fun login(emailOrGsm: String, password: String) {
        val validEmail: Boolean = Validator.validEmail(emailOrGsm)
        val validGsm: Boolean = Validator.validGsmWithMask(emailOrGsm)
        val validPassword: Boolean = Validator.validPassword(password)
        val loginInputType: Int = Validator.getInputType(emailOrGsm)

        val login = if (loginInputType == Validator.INPUT_TYPE_GSM) {
            val numericGsm: String = Formatter.getNumeric(emailOrGsm).substring(1)
            LoginGsm(LoginInfoGsm(numericGsm, password))
        } else {
            LoginEmail(LoginInfoEmail(emailOrGsm, password))
        }

        if ((validEmail || validGsm) && validPassword) {
            /*loginView.showLoadingView()
            val simpleObserver: SimpleObserver<Result> =
                object : SimpleObserver<Result?>(app, messageView) {
                    fun success(result: Result) {
                        super.success(result)
                        val otp: Otp = Gson().fromJson(result.getResult(), Otp::class.java)
                        sessionInfo.setSessionToken(otp.getSessionToken())
                        loginView.goToOtp(otp, serviceManager.loginWithOtp(login))
                    }

                    fun onComplete() {
                        super.onComplete()
                        loginView.hideLoadingView()
                    }
                }
            subsLoginWithOtp.add(serviceManager.loginWithOtp(login).subscribeWith(simpleObserver))*/
        } else {
            var type: ValidationErrorType = ValidationErrorType.ALL
            if (!validPassword) {
                type = ValidationErrorType.PASSWORD
            }
            if (loginInputType == Validator.INPUT_TYPE_GSM && !validGsm) {
                type = ValidationErrorType.GSM
            }
            if (loginInputType == Validator.INPUT_TYPE_EMAIL && !validEmail) {
                type = ValidationErrorType.EMAIL
            }
            if (loginInputType == Validator.INPUT_TYPE_UNDEFINED && !validEmail && !validGsm) {
                type = ValidationErrorType.EMAIL_GSM
            }
            val message: String = Validator.getValidationError(type)
//            loginView.showValidationError(message)
        }
    }
}