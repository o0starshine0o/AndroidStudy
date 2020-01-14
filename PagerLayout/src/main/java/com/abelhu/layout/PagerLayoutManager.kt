package com.abelhu.layout

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max


class PagerLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        // 如果没有children，不进行布局
        if (itemCount <= 0 || state.isPreLayout) return
        // 缓存所有的children
        detachAndScrapAttachedViews(recycler)
        // 排除所有已经布置完成的children后剩余的空间
        var remainWidth = width
        var remainHeight = height
        // 记录每一层child的最大高度
        var maxHeight = 0
        // 布局每一个child
        for (position in 0 until itemCount) {
            // 获取child
            val child = recycler.getViewForPosition(position)
            // 测量child的宽高
            measureChildWithMargins(child, 0, 0)
            val childWidth = getDecoratedMeasuredWidth(child)
            val childHeight = getDecoratedMeasuredHeight(child)
            // 判断水平方向是否有足够的空间给此child使用
            if (remainWidth >= childWidth) {
                // 有足够的空间，放置此child
                addView(child)
                layoutDecoratedWithMargins(
                    child,
                    width - remainWidth,
                    height - remainHeight,
                    width - remainWidth + childWidth,
                    height - remainHeight + childHeight
                )
                maxHeight = max(maxHeight, childHeight)
                // 更新剩余空间的可用宽度
                remainWidth -= childWidth
            } else {
                // 空间不够，尝试向下申请空间， 首先计算剩余空间的高度
                remainHeight -= maxHeight
                // 如果剩余空间的高度可以放下child，放置此child
                if (remainHeight >= childHeight) {
                    // 重置剩余空间的宽度
                    remainWidth = width
                    // 放置child
                    addView(child)
                    layoutDecoratedWithMargins(
                        child,
                        width - remainWidth,
                        height - remainHeight,
                        width - remainWidth + childWidth,
                        height - remainHeight + childHeight
                    )
                    maxHeight = max(0, childHeight)
                    // 更新剩余空间的可用宽度
                    remainWidth -= childWidth
                } else {
                    // 剩余空间的高度不足以放下child，结束对children的布局
                    break
                }
            }
        }

    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        offsetChildrenHorizontal(-dx)
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

    }
}