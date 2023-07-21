package com.abelhu.androidstudy.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView

class TouchWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : WebView(context, attrs, defStyleAttr, defStyleRes) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // touch事件的view入口
        // 返回super, 则进入本控件的onTouchEvent处理
        // 返回true, 消费掉
        // 返回false, 交给父控件的onTouchEvent处理
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 消费touch事件, 返回true
        // 不消费touch事件, 返回false, 交给父控件的onTouchEvent处理
        return super.onTouchEvent(event)
    }
}