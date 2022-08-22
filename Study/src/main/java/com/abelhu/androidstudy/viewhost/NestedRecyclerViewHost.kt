package com.abelhu.androidstudy.viewhost

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.abelhu.androidstudy.utils.logD
import kotlin.math.absoluteValue
import kotlin.math.sign

class NestedRecyclerViewHost : FrameLayout {
    private companion object {
        private const val TAG = "NestedRecyclerViewHost"

        private const val SCALED_FACTOR_HALF = 0.5f
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f

    private val parentRecyclerView: RecyclerView?
        get() {
            var v: View? = parent as? View
            while (v != null && v !is RecyclerView) {
                v = v.parent as? View
            }
            return v as? RecyclerView
        }

    private val child: View? get() = if (childCount > 0) getChildAt(0) else null

    private fun canChildScroll(orientation: Int, delta: Float): Boolean {
        val direction = -delta.sign.toInt()
        return when (orientation) {
            HORIZONTAL -> child?.canScrollHorizontally(direction) ?: false
            VERTICAL -> child?.canScrollVertically(direction) ?: false
            else -> throw IllegalArgumentException()
        }.logD(TAG) { "-->canChildScroll()--orientation:$orientation, delta:$delta, direction:$direction, result:$this" }
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        logD(TAG) { "-->onInterceptTouchEvent()--e:$e" }
        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)
    }

    @SuppressWarnings("ComplexMethod")
    private fun handleInterceptTouchEvent(e: MotionEvent) {
        logD(TAG) { "-->handleInterceptTouchEvent()--1--e:$e|parentViewPager:$parentRecyclerView" }
        val orientation = when (val manager = parentRecyclerView?.layoutManager) {
            is LinearLayoutManager -> manager.orientation
            is GridLayoutManager -> manager.orientation
            else -> return
        }

        // Early return if child can't scroll in same direction as parent
        if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
            logD(TAG) { "-->handleInterceptTouchEvent()--2--just return for child can't scroll in same direction as parent" }
            return
        }

        if (e.action == MotionEvent.ACTION_DOWN) {
            initialX = e.x
            initialY = e.y
            parent.requestDisallowInterceptTouchEvent(true)
            logD(TAG) { "requestDisallowInterceptTouchEvent(true), event: $e" }
        } else if (e.action == MotionEvent.ACTION_MOVE) {
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isHorizontal = orientation == HORIZONTAL

            // assuming ViewPager2 touch-slop is 2x touch-slop of child
            val scaledDx = dx.absoluteValue * if (isHorizontal) SCALED_FACTOR_HALF else 1f
            val scaledDy = dy.absoluteValue * if (isHorizontal) 1f else SCALED_FACTOR_HALF

            if (scaledDx > touchSlop || scaledDy > touchSlop) {
                if (isHorizontal == (scaledDy > scaledDx)) {
                    // Gesture is perpendicular, allow all parents to intercept
                    parent.requestDisallowInterceptTouchEvent(false)
                    logD(TAG) { "Gesture is perpendicular, allow all parents to intercept" }
                } else {
                    // Gesture is parallel, query child if movement in that direction is possible
                    if (canChildScroll(orientation, if (isHorizontal) dx else dy)) {
                        // Child can scroll, disallow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(true)
                        logD(TAG) { "Child can scroll, disallow all parents to intercept" }
                    } else {
                        // Child cannot scroll, allow all parents to intercept
                        parent.requestDisallowInterceptTouchEvent(false)
                        logD(TAG) { "Child cannot scroll, allow all parents to intercept" }
                    }
                }
            }
        }
    }
}