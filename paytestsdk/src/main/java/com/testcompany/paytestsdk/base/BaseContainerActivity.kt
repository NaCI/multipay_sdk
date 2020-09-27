package com.testcompany.paytestsdk.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.R
import com.testcompany.paytestsdk.util.updateBaseContextLocale
import com.testcompany.paytestsdk.util.addFragment

abstract class BaseContainerActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        if (newBase == null) {
            super.attachBaseContext(newBase)
        } else {
            super.attachBaseContext(updateBaseContextLocale(newBase, PayTest.getComponent().language().id))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        val fragment = supportFragmentManager.findFragmentById(R.id.layout_container)
        if (fragment == null) {
            addFragment(fragment(), R.id.layout_container)
        }
    }

    protected abstract fun fragment(): Fragment
}