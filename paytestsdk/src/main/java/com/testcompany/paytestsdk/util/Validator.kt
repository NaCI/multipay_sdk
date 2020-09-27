package com.testcompany.paytestsdk.util

import android.util.Patterns
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.R
import com.testcompany.paytestsdk.data.model.type.ValidationErrorType

object Validator {

    const val INPUT_TYPE_EMAIL = 0
    const val INPUT_TYPE_GSM = 1
    const val INPUT_TYPE_UNDEFINED = 2
    private const val PASSWORD_MIN = 8
    private const val PASSWORD_MAX = 20
    private const val LENGTH_PHONE = 10
    private const val MASKED_LENGTH_PHONE = 16
    private const val LENGTH_PIN = 4
    private const val LENGTH_CARD_NUMBER = 16
    private const val CVV_MIN = 3
    private const val VERIFICATION_CODE = 6
    private const val PREFIX_PHONE = "5"
    private const val MASKED_PREFIX_PHONE = "0"
    private const val REGEX_NAME = "^[\\p{L} .'-]+$"
    private const val REGEX_SURNAME = REGEX_NAME

    fun atLeastOneAlpha(str: String): Boolean {
        return str.matches(".*[a-zA-Z]+.*".toRegex())
    }

    fun getInputType(str: String): Int {
        val inputType: Int
        inputType = if (str.isEmpty()) {
            INPUT_TYPE_UNDEFINED
        } else if (atLeastOneAlpha(str)) {
            INPUT_TYPE_EMAIL
        } else {
            INPUT_TYPE_GSM
        }
        return inputType
    }

    fun validName(name: String): Boolean {
        return (!name.isNullOrEmpty() && name.matches(REGEX_NAME.toRegex()))
    }

    fun validSurname(surname: String): Boolean {
        return (!surname.isNullOrEmpty() && surname.matches(REGEX_SURNAME.toRegex()))
    }

    fun validEmail(email: String?): Boolean {
        return (!email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun validPassword(password: String): Boolean {
        return (!password.isNullOrEmpty()
                && password.length >= PASSWORD_MIN && password.length <= PASSWORD_MAX)
    }

    fun validPasswordWithRegex(password: String): Boolean {
        return (password.matches("^(?=.*[0-9])(?=.*[a-z]).{8,20}$".toRegex())
                || password.matches("^(?=.*[0-9])(?=.*[A-Z]).{8,20}$".toRegex()))
    }

    fun validPasswordRepeat(password: String, passwordRepeat: String): Boolean {
        return password == passwordRepeat
    }

    fun validGsm(gsm: String): Boolean {
        return (!gsm.isNullOrEmpty()
                && gsm.length == LENGTH_PHONE && gsm.startsWith(PREFIX_PHONE))
    }

    fun validGsmWithMask(gsm: String): Boolean {
        return (!gsm.isNullOrEmpty()
                && gsm.length == MASKED_LENGTH_PHONE && gsm.startsWith(MASKED_PREFIX_PHONE))
    }

    fun validPinCode(pinCode: String): Boolean {
        return (!pinCode.isNullOrEmpty()
                && pinCode.length == LENGTH_PIN)
    }

    fun validCardNumber(cardNumber: String): Boolean {
        return cardNumber.length == LENGTH_CARD_NUMBER && !atLeastOneAlpha(cardNumber)
    }

    fun validAmount(amount: Double): Boolean {
        return amount != 0.0
    }

    fun validCvv(cvv: String): Boolean {
        return !cvv.isNullOrEmpty() && cvv.length >= CVV_MIN
    }

    fun validDay(day: Int): Boolean {
        return day in 1..31
    }

    fun validMonth(month: String?): Boolean {
        return !month.isNullOrEmpty()
    }

    fun validYear(year: String?): Boolean {
        return !year.isNullOrEmpty()
    }

    fun validAlias(alias: String?): Boolean {
        return !alias.isNullOrEmpty()
    }

    fun validOpyId(opyId: String): Boolean {
        return !opyId.isNullOrEmpty() && opyId != "0"
    }

    fun validVerificationCode(code: String): Boolean {
        return !code.isNullOrEmpty() && code.length == VERIFICATION_CODE
    }

    //TODO : string xml üzerinde stringleri düzelt
    fun getValidationError(type: ValidationErrorType): String {
        when {
            type === ValidationErrorType.NAME -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_Name)
            }
            type === ValidationErrorType.SURNAME -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_Surname)
            }
            type === ValidationErrorType.EMAIL -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.PersonalInfoSettings_Error_EmailFormat)
            }
            type === ValidationErrorType.GSM -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_Phone)
            }
            type === ValidationErrorType.PASSWORD -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_Password)
            }
            type === ValidationErrorType.EMAIL_GSM -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_EmailorGSM)
            }
            type === ValidationErrorType.PASSWORD_NEW -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.validation_error_password_new)
            }
            type === ValidationErrorType.PASSWORD_OLD -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.validation_error_password_old)
            }
            type === ValidationErrorType.PASSWORD_NEW_REPEAT -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.validation_error_password_new_repeat)
            }
            type === ValidationErrorType.PASSWORDS_NOT_MATCH -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.ChangePassword_Error_PasswordsIsNotEqual)
            }
            type === ValidationErrorType.PIN -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.validation_error_pin)
            }
            type === ValidationErrorType.TOP_UP_AMOUNT -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Topup_Error_Amount)
            }
            type === ValidationErrorType.NAME_SURNAME -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_Name)
            }
            type === ValidationErrorType.TOP_UP_CARD_NUMBER -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_CardNumber)
            }
            type === ValidationErrorType.TOP_UP_CVV -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_CVV)
            }
            type === ValidationErrorType.TOP_UP_EXPIRE_DATE -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_Expire)
            }
            type === ValidationErrorType.ALIAS -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_Alias)
            }
            type === ValidationErrorType.ACTIVATION_CODE -> {
                return PayTest.getComponent().requireAppContext().getString(R.string.Validation_ActivationCode)
            }
            else -> return StringUtils.EMPTY
        }
    }
}