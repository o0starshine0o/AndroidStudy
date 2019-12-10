package com.abelhu.androidstudy.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
 * 设置屏幕全屏状态下的显示模式
 *
 * @param activity            Activity
 * @param isShowStatusBar     是否显示状态栏
 * @param isGray              状态栏是否使用灰色字体
 * @param isShowNavigationBar 是否显示导航栏
 * @param isInsertProfiled    是否允许页面延伸到刘海区域
 */
fun setFullscreen(activity: Activity?, isShowStatusBar: Boolean, isGray: Boolean, isShowNavigationBar: Boolean, isInsertProfiled: Boolean) {
    setFullscreen(activity?.window, isShowStatusBar, isGray, isShowNavigationBar, isInsertProfiled)
}

/**
 * 设置屏幕全屏状态下的显示模式
 *
 * @param window              窗口
 * @param isShowStatusBar     是否显示状态栏
 * @param isGray              状态栏是否使用灰色字体
 * @param isShowNavigationBar 是否显示导航栏
 * @param isInsertProfiled    是否允许页面延伸到刘海区域
 */
fun setFullscreen(window: Window?, isShowStatusBar: Boolean, isGray: Boolean, isShowNavigationBar: Boolean, isInsertProfiled: Boolean) {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) { // 在JELLY_BEAN之前，只能控制全部隐藏
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else { // 清除默认配置
        window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // 可以保持布局稳定，避免在显隐状态栏导航栏的时候发生布局的变化。
        var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        // 设置状态栏的文字颜色为灰色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isGray) {
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        // 可以让布局延伸到状态栏的位置
        uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (!isShowStatusBar) { // 该属性是用来隐藏状态栏的
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        if (!isShowNavigationBar) { // 可以让布局延伸到导航栏的位置
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            // 该属性是用来隐藏导航栏的
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
        // 设置这个属性后。当状态栏隐藏的时候，手动调出状态栏导航栏，显示一会儿随后就会隐藏掉。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
        window?.decorView?.systemUiVisibility = uiOptions
        // 专门设置一下状态栏导航栏背景颜色为透明，凸显效果。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.navigationBarColor = Color.TRANSPARENT
            window?.statusBarColor = Color.TRANSPARENT
        }
        // 适配刘海屏、水滴屏等异形屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && isInsertProfiled) {
            val lp = window?.attributes
            // 允许页面延伸到刘海区域
            lp?.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window?.attributes = lp
        }
    }
}