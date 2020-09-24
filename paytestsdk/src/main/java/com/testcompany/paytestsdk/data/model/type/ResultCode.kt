package com.testcompany.paytestsdk.data.model.type

enum class ResultCode(val value: Int) {
    SUCCESS(0),
    SESSION_INVALID(21451),
    AGREEMENT_OUT_OF_DATE(21452)
}