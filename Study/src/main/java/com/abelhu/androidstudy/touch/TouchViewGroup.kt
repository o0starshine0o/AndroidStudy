package com.abelhu.androidstudy.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class TouchViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?=null,
    defStyleAttr: Int=0,
    defStyleRes: Int=0) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // touch事件viewGroup的入口
        // 如果返回true, 则touch事件到此为止
        // 返回false, 则返回给父控件的onTouchEvent处理
        // 返回super, 先调用onInterceptTouchEvent, 然后继续向下分发touch
        // 如果这里返回down事件返回true, 则所有能收到down事件的函数也能收到接下来的move, up事件
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // 在dispatchTouchEvent中被调用
        // 是否需要拦截touch事件, 返回ture, 给本控件的onTouchEvent处理
        // 如果不拦截, 返回false或者super, 则继续向子节点分发, 调用到dispatchTouchEvent
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // 消费touch事件, 返回true, 不消费touch事件, 发挥false
        // 如果down事件在这里返回true, 那么接下来的move和up事件, 从根控件开始传递, 但是就只能走到这里, 不会向子控件传递
        return super.onTouchEvent(event)
    }
}