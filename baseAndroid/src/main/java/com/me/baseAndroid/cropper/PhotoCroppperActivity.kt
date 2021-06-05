package com.me.baseAndroid.cropper

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.me.baseAndroid.R
import com.me.baseAndroid.toolbar.ToolbarActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.back_arch_module_photo_cropper_activity.*
import kotlinx.android.synthetic.main.base_arch_module_layout_toolbar.*
import java.io.File
import java.io.IOException
import java.io.Serializable

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
class PhotoCropperConfig : Serializable {

    var cropShape: CropImageView.CropShape = CropImageView.CropShape.OVAL
    var authority: String? = null
    var openCameraOnly: Boolean = false
    var openChooserOnly: Boolean = false
    var saveColor: Int? = null
    var cancelColor: Int? = null
    var save: String? = null
    var title: String = ""

    var width: Int? = defaultWidth

    var quality: Int = defaultQuality

    var minWindowWidth: Int? = defaultMinWindowWidth

    var disableCropping: Boolean = false

    companion object {
        const val defaultWidth = 1000
        const val defaultQuality = 90

        const val defaultMinWindowWidth = 900
    }

}

class PhotoCropperActivity : ToolbarActivity() {

    private var config: PhotoCropperConfig? = null
    private var uriToCrop: Uri? = null

    override val layoutId = R.layout.back_arch_module_photo_cropper_activity

    companion object {
        const val EXTRA_CONFIG = "EXTRA_CONFIG"
        const val EXTRA_RESULT = "EXTRA_RESULT"
        const val URI_TO_CROP = "URI_TO_CROP"
        const val CODE = 1001
        const val CODE_CAMERA = 101
        const val CODE_CHOOSER = 102
        fun start(
            context: Context, fragment: Fragment,
            photoCropperConfig: PhotoCropperConfig,
            uriToCrop: Uri?
        ) {
            val intent = Intent(context, PhotoCropperActivity::class.java)
            intent.putExtra(EXTRA_CONFIG, photoCropperConfig)
            intent.putExtra(URI_TO_CROP, uriToCrop)
            fragment.startActivityForResult(intent, CODE)
        }

        fun handleResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
        ): Uri? {
            if (requestCode == CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    return data?.getParcelableExtra(EXTRA_RESULT) as? Uri
                }
            }
            return null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        config = intent.getSerializableExtra(EXTRA_CONFIG) as PhotoCropperConfig?
        uriToCrop = intent.getParcelableExtra<Uri>(URI_TO_CROP)

        cropImageView.setAspectRatio(1, 1)
        cropImageView.cropShape = config?.cropShape ?: CropImageView.CropShape.OVAL

        cropImageView.isAutoZoomEnabled = false

        val requiredWidth = config?.width ?: PhotoCropperConfig.defaultWidth
        val minWidth = config?.minWindowWidth ?: PhotoCropperConfig.defaultMinWindowWidth
        cropImageView.setMinCropResultSize(minWidth, minWidth)


        tv_title.text = config?.title
        config?.save?.let {
            tv_save.text = it
        }

        tv_cancel.setOnClickListener {
            onBackPressed()
        }
        tv_save.setOnClickListener {
            cropImageView.cropShape = CropImageView.CropShape.RECTANGLE
            outputUri?.let {
                cropImageView.saveCroppedImageAsync(
                    it,
                    Bitmap.CompressFormat.JPEG,
                    config?.quality ?: PhotoCropperConfig.defaultQuality,
                    requiredWidth,
                    requiredWidth
                )

            }
        }

        config?.saveColor?.let {
            tv_save.setTextColor(it)
        }
        config?.cancelColor?.let {
            tv_cancel.setTextColor(it)
        }

        cropImageView.setOnCropImageCompleteListener { _, result ->
            val resultCode =
                if (result.error == null) Activity.RESULT_OK else Activity.RESULT_CANCELED
            returnIntentResult(result.uri, resultCode)
        }
        cropImageView.setOnSetImageUriCompleteListener { _, _, error ->
            if (error == null) {
                cropImageView.cropShape = CropImageView.CropShape.OVAL
            } else {
                cancel()
            }
        }

        if (config?.openCameraOnly == true) {
            startCameraIntent()
        } else if (config?.openChooserOnly == true) {
            startChooserIntent()
        }

        if (uriToCrop != null) {
            cropImageView.setImageUriAsync(uriToCrop)
        }

        cropImageView.guidelines = CropImageView.Guidelines.ON
        cropImageView.setMultiTouchEnabled(true)

    }

    private fun returnIntentResult(
        uri: Uri?,
        resultCode: Int
    ) {
        val intent = Intent()
        intent.putExtras(getIntent())
        intent.putExtra(EXTRA_RESULT, uri)
        setResult(resultCode, intent)
        finish()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        cancel()
    }

    private fun cancel() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun loadUri(uriToCrop: Uri?) {
        if (uriToCrop != null) {
            cropImageView.setImageUriAsync(uriToCrop)
        }
    }

    private val outputUri: Uri? by lazy {
        try {
            FileProvider.getUriForFile(
                this, config?.authority ?: "",
                File.createTempFile(
                    "cropped",
                    ".png",
                    cacheDir
                )
            )

        } catch (e: IOException) {
            null
        }
    }


    fun startCameraIntent() {

        if (checkCameraPermission()) {
            openCameraIntent()
        } else {
            requestCameraPermission()
        }

    }

    fun startChooserIntent() {

        if (checkChooserPermission()) {
            openChooserIntent()
        } else {
            requestChooserPermission()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCameraIntent()
                    return
                }
            }
            CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openChooserIntent()
                    return
                }
            }
            else -> {
                return
            }
        }
    }


    fun openCameraIntent() {
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        startActivityForResult(takePhotoIntent, CODE_CAMERA)
    }

    fun openChooserIntent() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, CODE_CHOOSER)
    }

    fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkChooserPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestCameraPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.CAMERA),
            CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE
        )
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestChooserPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODE_CHOOSER) {
                val uri = CropImage.getPickImageResultUri(this, data)
                if (config?.disableCropping == true) {
                    returnIntentResult(uri, Activity.RESULT_OK)
                } else {
                    cropImageView.setImageUriAsync(uri)
                }
            } else if (requestCode == CODE_CAMERA) {
                if (config?.disableCropping == true) {
                    returnIntentResult(outputUri, Activity.RESULT_OK)
                } else {
                    cropImageView.setImageUriAsync(outputUri)
                }
            }
        } else {
            cancel()
        }

    }


}
