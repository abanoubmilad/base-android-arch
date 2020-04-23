package com.me.base_android.fullNav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import com.me.base_android.R
import com.me.base_android.base.BaseFragment
import com.me.base_android.nav.NavFragment
import com.me.base_android.view.executeIfAdded

class DummyFragment(private val index: Int) : BaseFragment(),
    FragmentManager.OnBackStackChangedListener {
    override val layoutId = R.layout.dummy_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.addOnBackStackChangedListener(this)

        (activity as? HomeNavActivityFull)?.navTabFragments?.getOrNull(index)?.invoke()?.let {
            childFragmentManager
                .beginTransaction().addToBackStack(null)
                .add(R.id.fl_container, it)
                .commitAllowingStateLoss()
        }

    }


    override fun onBackStackChanged() {
        childFragmentManager.fragments.lastOrNull()?.executeIfAdded { last ->
            (last as? NavFragment)?.onVisible()
        }


    }

}