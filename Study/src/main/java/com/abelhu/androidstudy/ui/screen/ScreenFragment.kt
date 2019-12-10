package com.abelhu.androidstudy.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abelhu.androidstudy.R

class ScreenFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_screen, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val decorView = activity?.window?.decorView
        var uiOptions: Int = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        decorView?.systemUiVisibility = uiOptions

    }
}