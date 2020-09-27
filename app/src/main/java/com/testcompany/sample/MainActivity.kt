package com.testcompany.sample

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.PayTestListener
import com.testcompany.paytestsdk.data.model.response.BaseResponse

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)

        findViewById<Button>(R.id.button).setOnClickListener {
            PayTest.startSDKForSubmitConsumer(this, object : PayTestListener {
                override fun <T : BaseResponse> onSuccess(response: T?) {
                    Log.d(TAG, "onSuccess: $response")
                }

                override fun onError(error: String?, code: Int) {
                    Log.d(TAG, "onError: $error")
                }

            })
        }
    }
}