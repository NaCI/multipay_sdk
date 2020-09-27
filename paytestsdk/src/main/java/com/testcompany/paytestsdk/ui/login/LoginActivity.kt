package com.testcompany.paytestsdk.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.testcompany.paytestsdk.PayTestListener
import com.testcompany.paytestsdk.base.BaseContainerActivity

internal class LoginActivity : BaseContainerActivity() {

    lateinit var listener: PayTestListener

    override fun onCreate(savedInstanceState: Bundle?) {
        listener = intent.getSerializableExtra("listener") as PayTestListener
        super.onCreate(savedInstanceState)
    }

    override fun fragment(): Fragment = LoginFragment.newInstance(listener)
}