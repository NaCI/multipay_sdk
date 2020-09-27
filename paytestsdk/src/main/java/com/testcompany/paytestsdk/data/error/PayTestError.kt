package com.testcompany.paytestsdk.data.error

import com.testcompany.paytestsdk.data.model.response.Result

class PayTestError : Exception {
    var errorCode = 0
        private set
    var statusCode = 0
        private set
    var data: ByteArray? = null
        private set
    var result: Result? = null
        private set

    private constructor(detailMessage: String) : super(detailMessage)

    private constructor(detailMessage: String, throwable: Throwable) : super(
        detailMessage,
        throwable
    )

    private constructor(throwable: Throwable) : super(throwable)

    override fun toString(): String {
        return "PayTestError{errorCode=$errorCode, statusCode=$statusCode, data=" + (if (data != null) String(
            data!!
        ) else "") + ", cause=" + (cause?.toString() ?: "") + ", message=" + message + '}'
    }

    companion object {
        const val ERROR_CODE_CANCEL = -2
        const val ERROR_CODE_NO_CONNECTION = -1
        const val ERROR_GENERAL = 0
        const val ERROR_SERVER = 3
        const val ERROR_SERIALIZATION_FAILED = 9998
        const val ERROR_API_KEY_DOES_NOT_EXISTS = 9997
        const val ERROR_INBOX_DOES_NOT_HAVE_NEXT_PAGE = 9996
        const val ERROR_INVALID_PARAMETERS = 9995
        const val ERROR_INVALID_RESPONSE = 9994
        fun noApiKeyInstance(): PayTestError {
            val error =
                PayTestError("There is no API Key that is set. You should give your API key to SDK via PayTest.setApiKey method.")
            error.errorCode = ERROR_API_KEY_DOES_NOT_EXISTS
            return error
        }

        fun responseSerializationFailedInstance(throwable: Throwable): PayTestError {
            val error = PayTestError("Error during converting response to class.", throwable)
            error.errorCode = ERROR_SERIALIZATION_FAILED
            return error
        }

        fun invalidResponseInstance(): PayTestError {
            val error = PayTestError("A non-jsonObject response received from server.")
            error.errorCode = ERROR_INVALID_RESPONSE
            return error
        }

        fun requestSerializationFailedInstance(throwable: Throwable): PayTestError {
            val error = PayTestError("Error during converting request to jsonObject.", throwable)
            error.errorCode = ERROR_SERIALIZATION_FAILED
            return error
        }

        fun inboxDoesNotHaveNextPageInstance(): PayTestError {
            val error =
                PayTestError("Inbox does not have more pages. You can check this via hasNextPage property.")
            error.errorCode = ERROR_INBOX_DOES_NOT_HAVE_NEXT_PAGE
            return error
        }

        fun invalidParametersInstance(): PayTestError {
            val error = PayTestError("Given parameters are invalid.")
            error.errorCode = ERROR_INVALID_PARAMETERS
            return error
        }

        fun noConnectionInstance(): PayTestError {
            val error = PayTestError("No network connection.")
            error.errorCode = ERROR_CODE_NO_CONNECTION
            return error
        }

        @JvmOverloads
        fun generalInstance(errorMessage: String = "An error occurred."): PayTestError {
            val error = PayTestError(errorMessage)
            error.errorCode = ERROR_GENERAL
            return error
        }

        fun serverErrorInstance(
            errorMessage: String,
            statusCode: Int,
            data: ByteArray?
        ): PayTestError {
            val error = PayTestError(errorMessage)
            error.errorCode = ERROR_SERVER
            error.statusCode = statusCode
            error.data = data
            return error
        }

        fun payTestErrorInstance(
            errorMessage: String?,
            statusCode: Int,
            result: Result?
        ): PayTestError {
            val error = PayTestError(errorMessage ?: "An Error Occured")
            error.errorCode = ERROR_SERVER
            error.statusCode = statusCode
            error.result = result
            return error
        }

        fun cancelInstance(): PayTestError {
            val error = PayTestError("Request has been cancelled.")
            error.errorCode = ERROR_CODE_CANCEL
            return error
        }
    }
}
