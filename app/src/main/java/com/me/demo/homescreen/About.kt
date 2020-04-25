package com.me.demo.homescreen

import android.content.Intent
import com.me.baseAndroid.cropper.PhotoCropperActivity
import com.me.baseAndroid.cropper.PhotoCropperConfig
import com.me.baseAndroid.nav.NavFragment
import com.me.baseAndroid.view.getPrivateViewModel
import com.me.baseAndroid.view.highLightKeyword
import com.me.baseAndroid.view.startUrlIntent
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
    override fun onCreated() {

        viewModel.checkMe()

        btn.setOnClickListener {
            INav?.navigate(About2())
        }
        about_tv.highLightKeyword("About 111111 aaaa", listOf("A", "a"), onClicks = listOf({
            activity.startUrlIntent("google.com")
        }, {
            activity.startUrlIntent("google.com")
        }), color = R.color.colorPrimary)
        about_tv2.highLightKeyword("About 111111 aaaa", "A", onClick = {
            activity.startUrlIntent("google.com")
        })
        about_tv.text = "hello mannnnnnnnzzz"
        about_tv.text = "hello mannnnnnnnzzz222222"

    }


    override fun onVisible() {

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
