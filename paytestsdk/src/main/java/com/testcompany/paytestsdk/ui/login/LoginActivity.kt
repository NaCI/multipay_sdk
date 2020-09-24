package com.testcompany.paytestsdk.ui.login

import androidx.fragment.app.Fragment
import com.testcompany.paytestsdk.base.BaseContainerActivity

class LoginActivity : BaseContainerActivity() {

    override fun fragment(): Fragment = LoginFragment.newInstance()
}