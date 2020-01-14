package com.abelhu.layout

import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max


class PagerLayoutManager(private val spanCount: Int = 12, private val spanSizeLookup: (position: Int) -> Int = { _ -> 12 }) : RecyclerView.LayoutManager() {
//    /**
//     * 记录每一个child的边界
//     * 第i个child的起始位置{@link #cachedBorders}[i-1] + 1， 结束位置为{@link #cachedBorders}[i]
//     */
//    private var cachedBorders: IntArray? = null

//    init {
//        // 根据spanCount计算cachedBorders，记录每个child的边界
//        cachedBorders = IntArray(spanCount + 1){i -> i * width / spanCount}
//    }

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
        var page = 0
        // 布局每一个child
        for (position in 0 until itemCount) {
            // 获取child
            val child = recycler.getViewForPosition(position)
            // 根据spanSizeLookup动态分配child的宽度
            assignSpans(child, position)
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
                    page * width + width - remainWidth,
                    height - remainHeight,
                    page * width + width - remainWidth + childWidth,
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
                        page * width + width - remainWidth,
                        height - remainHeight,
                        page * width + width - remainWidth + childWidth,
                        height - remainHeight + childHeight
                    )
                    maxHeight = max(0, childHeight)
                    // 更新剩余空间的可用宽度
                    remainWidth -= childWidth
                } else {
                    // 剩余空间的高度不足以放下child，开启新的一页，用以放置child
                    page += 1
                    // 重置可用宽高
                    remainWidth = width
                    remainHeight = height
                    // 默认有足够的空间，放置此child
                    addView(child)
                    layoutDecoratedWithMargins(
                        child,
                        page * width,
                        height - remainHeight,
                        page * width + childWidth,
                        height - remainHeight + childHeight
                    )
                    maxHeight = max(0, childHeight)
                    // 更新剩余空间的可用宽度
                    remainWidth -= childWidth
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

    /**
     * 为child分配宽度
     */
    private fun assignSpans(view: View, position: Int) {
        val width = spanSizeLookup.invoke(position) * width / spanCount
        view.layoutParams.width = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
    }

    fun onLayoutChild() {

    }

    fun fill(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

    }
}