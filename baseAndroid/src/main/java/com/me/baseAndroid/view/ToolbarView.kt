package com.me.baseAndroid.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.me.baseAndroid.R
import kotlinx.android.synthetic.main.base_arch_module_layout_toolbar.view.*

/*
 * *
 *  * Created by Abanoub Milad Nassief Hanna
 *  * on 5/1/20 11:05 PM
 *  * Last modified 5/1/20 11:05 PM
 *
 */
class ToolbarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.base_arch_module_layout_toolbar, this)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ToolbarView, 0, 0
            )

            // background is white by default
            rl_toolbar.setBackgroundResource(
                typedArray
                    .getResourceId(
                        R.styleable
                            .ToolbarView_barColor, android.R.color.white
                    )
            )

            // text color is black by default
            tv_title.setTextColor(
                ContextCompat.getColor(
                    context, typedArray
                        .getResourceId(
                            R.styleable
                                .ToolbarView_textColor, android.R.color.black
                        )
                )
            )

            // title is empty by default
            val resource = typedArray
                .getResourceId(
                    R.styleable
                        .ToolbarView_title, 0
                )
            tv_title.text = if (resource == 0) typedArray.getString(
                R.styleable
                    .ToolbarView_title
            ) else resources.getText(resource)


            // left button is visible by default
            val showLeftButton =
                typedArray
                    .getBoolean(
                        R.styleable
                            .ToolbarView_showLeftButton, true
                    )

            iv_left.visibility = if (showLeftButton) View.VISIBLE else View.GONE

            if (showLeftButton) {
                iv_left.setImageResource(
                    typedArray
                        .getResourceId(
                            R.styleable
                                .ToolbarView_leftButton, R.drawable.base_arch_module_ic_arrow_back
                        )
                )
                val leftButtonTint =
                    typedArray
                        .getResourceId(
                            R.styleable
                                .ToolbarView_leftButtonTint, 0
                        )
                if (leftButtonTint != 0) {
                    ImageViewCompat.setImageTintList(
                        iv_left,
                        ColorStateList.valueOf(ContextCompat.getColor(context, leftButtonTint))
                    )

                }

            }

            val resourceContentDescriptionLeftButton = typedArray
                .getResourceId(
                    R.styleable
                        .ToolbarView_contentDescriptionLeftButton, 0
                )

            iv_left.contentDescription =
                if (resourceContentDescriptionLeftButton == 0) typedArray.getString(
                    R.styleable
                        .ToolbarView_contentDescriptionLeftButton
                ) else resources.getText(resourceContentDescriptionLeftButton)

            typedArray.recycle()
        }
    }

    fun setTitle(title: String) {
        tv_title.text = title
    }
}
