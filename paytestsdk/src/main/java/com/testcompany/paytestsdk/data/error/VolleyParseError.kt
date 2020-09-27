package com.testcompany.paytestsdk.data.error

import com.android.volley.NetworkResponse
import com.android.volley.VolleyError

internal class VolleyParseError(response: NetworkResponse?) : VolleyError(response)
