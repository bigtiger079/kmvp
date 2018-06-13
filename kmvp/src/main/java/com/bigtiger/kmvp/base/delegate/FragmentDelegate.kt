package com.bigtiger.kmvp.base.delegate

import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.view.View


interface FragmentDelegate {

    /**
     * Return true if the fragment is currently added to its activity.
     */
    val isAdded: Boolean

    fun onAttach(context: Context)

    fun onCreate(@Nullable savedInstanceState: Bundle?)

    fun onCreateView(@Nullable view: View?, @Nullable savedInstanceState: Bundle?)

    fun onActivityCreate(@Nullable savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroyView()

    fun onDestroy()

    fun onDetach()

    companion object {
        val FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE"
    }
}
