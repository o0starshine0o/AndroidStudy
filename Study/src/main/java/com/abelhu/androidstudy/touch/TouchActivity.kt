package com.abelhu.androidstudy.touch

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.abelhu.androidstudy.R

/**
 * 查考链接:https://www.jianshu.com/p/a6e4137358ad
 */
class TouchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // touch事件的入口
        // 如果返回true或者false, 则touch被消费掉了
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // touch事件回到activity中, 由activity处理
        return super.onTouchEvent(event)
    }
}