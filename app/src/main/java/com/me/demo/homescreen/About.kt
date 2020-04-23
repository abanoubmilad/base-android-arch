package com.me.demo.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.me.base_android.cropper.PhotoCropperActivity
import com.me.base_android.cropper.PhotoCropperConfig
import com.me.base_android.nav.NavFragment
import com.me.base_android.view.getPrivateViewModel
import com.me.base_android.view.highLightKeyword
import com.me.base_android.view.startUrlIntent
import com.me.demo.BuildConfig
import com.me.demo.R
import kotlinx.android.synthetic.main.fragment_about.*

/**
 * Shows "About"
 */
class About : NavFragment() {
    override val layoutId = R.layout.fragment_about

    val viewModel by lazy {
        getPrivateViewModel(ViewModel::class.java)
    }
    private var firstCreation = true
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.checkMe()

        btn.setOnClickListener {
            // navigate(About2())
            onSelectImageClick()
        }
        about_tv.highLightKeyword("About 111111 aaaa", listOf("A", "a"), onClicks = listOf({
            activity.startUrlIntent("google.com")
        }, {
            activity.startUrlIntent("google.com")
        }), color = R.color.colorPrimary)
        about_tv2.highLightKeyword("About 111111 aaaa", "A", onClick = {
            activity.startUrlIntent("google.com")
        })
        about_tv.text = "hello"

    }


    override fun onVisible() {
        super.onVisible()

        showCookieBar("about 1")
    }

    fun onSelectImageClick() {
        context?.let { context ->
            PhotoCropperActivity.start(
                context,
                this
                , PhotoCropperConfig().apply {
                    authority = BuildConfig.APPLICATION_ID
                    openCameraOnly = true
                    openChooserOnly = false
                    saveColor = null
                    save = "Save"
                    title = "Profile Photo"
                    width = 1200
                    height = 1200
                    quality = 75
                }, null
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        PhotoCropperActivity.handleResult(requestCode, resultCode, data)?.let {
            image.setImageURI(it)
        }

    }

}
