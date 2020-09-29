package com.testcompany.paytestsdk.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.IdRes
import com.android.volley.toolbox.NetworkImageView
import com.testcompany.paytestsdk.PayTest
import com.testcompany.paytestsdk.R

internal class PayTestNetworkImageView(context: Context, attrs: AttributeSet) :
    NetworkImageView(context, attrs) {

    companion object {
        const val NO_RESOURCE_VALUE = 0
        val DEFAULT_IMAGE_RES = NO_RESOURCE_VALUE
        val DEFAULT_ERROR_RES = NO_RESOURCE_VALUE
    }

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val attributes: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.PayTestNetworkImageView)

        val errorImageRes: Int =
            attributes.getResourceId(
                R.styleable.PayTestNetworkImageView_error_image_res_id,
                DEFAULT_ERROR_RES
            )

        val defaultImageRes: Int =
            attributes.getResourceId(
                R.styleable.PayTestNetworkImageView_default_image_res_id,
                DEFAULT_IMAGE_RES
            )

        val imageRes: Int =
            attributes.getResourceId(
                R.styleable.PayTestNetworkImageView_image_res_id,
                NO_RESOURCE_VALUE
            )

        val imgSrc: String? =
            attributes.getString(R.styleable.PayTestNetworkImageView_img_url)

        if (errorImageRes > 0) {
            setErrorImageResId(errorImageRes)
        }

        if (defaultImageRes > 0) {
            setDefaultImageResId(defaultImageRes)
        }

        if (!imgSrc.isNullOrEmpty()) {
            setImageUrl(imgSrc)
        } else if (imageRes > 0) {
            setImageResource(imageRes)
        }

        attributes.recycle()
    }

    fun setImageUrl(url: String?) {
        setImageUrl(
            url,
            PayTest.getComponent().requestManager().getNetworkManager().getNetworkAdapter()
                .getVolleyImageLoader()
        )
    }

    fun setErrorImage(@IdRes resId: Int) {
        setErrorImageResId(resId)
    }

    fun setDefaultImage(@IdRes resId: Int) {
        setDefaultImageResId(resId)
    }
}