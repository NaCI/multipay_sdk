package com.testcompany.paytestsdk.util

internal object Formatter {

    private const val TAG = "Formatter"

    const val MONEY_DIVISION = 100

    private const val CARD_NUMBER_REGEX = "(\\d{4})(\\d{4})(\\d{1})(\\d{3})(\\d{4})"
    private const val CARD_NUMBER_REPLACEMENT = "$1 $2 $3$4 $5"
    private const val CARD_NUMBER_MASK_REPLACEMENT = "$1 **** *$4 $5"

    private const val BANK_CARD_NUMBER_REGEX = "(\\d{4})(\\d{2})(\\W{2})(\\W{4})(\\d{4})"
    private const val BANK_CARD_NUMBER_REPLACEMENT = "$1 $2$3 $4 $5"

    private const val PHONE_NUMBER_REGEX = "(\\d{3})(\\d{3})(\\d{2})(\\d{2})"
    private const val PHONE_NUMBER_REPLACEMENT = "0($1) $2 $3 $4"

    private const val DATE_FORMAT_SUCCESS = "dd MMMM yyyy"
    private const val TIME_FORMAT_SUCCESS = "HH:mm"

    // TODO : complete class
}