package com.me.baseAndroid.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.me.baseAndroid.base.BaseFragment

/**
 * Created by Abanoub Hanna.
 */
abstract class NavFragment : BaseFragment() {
    val INav
        get() = activity as? INav

    open val shouldSaveState = false

    private var hasInitializedRootView = false
    private var wasInVisibleBefore = false
    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (!shouldSaveState) {
            wasInVisibleBefore = false
            // Inflate the layout for this fragment
            return buildRootView(inflater, container)
        } else if (rootView == null) {
            wasInVisibleBefore = false
            // Inflate the layout for this fragment
            rootView = buildRootView(inflater, container)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            wasInVisibleBefore = true

            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!hasInitializedRootView || !shouldSaveState) {
            hasInitializedRootView = true
            onCreated()
            onVisible()
        }
    }

    abstract fun onCreated()

    abstract fun onVisible()

    fun onVisibleInternal() {
        if (hasInitializedRootView && wasInVisibleBefore) {
            wasInVisibleBefore = false
            onVisible()
        }
    }
}