package com.abelhu.androidstudy.ui.main.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abelhu.androidstudy.R
import com.abelhu.layout.PagerLayoutManager
import com.abelhu.layout.PagerSnapHelper
import kotlinx.android.synthetic.main.fragment_slideshow.*

class SlideFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slideshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.adapter = SlideAdapter()
        // 离屏缓存，并不会放入回收池，在反向滑动的时候保证item**不会**经过onBindViewHolder过程直接显示出来
        recyclerView.setItemViewCacheSize(0)
        // 根据每屏最多显示的item数量，设置其缓存阈值
        recyclerView.recycledViewPool.setMaxRecycledViews(SlideAdapter.TYPE_6, 20)
        recyclerView.recycledViewPool.setMaxRecycledViews(SlideAdapter.TYPE_4, 20)
        recyclerView.recycledViewPool.setMaxRecycledViews(SlideAdapter.TYPE_3, 4)
        recyclerView.recycledViewPool.setMaxRecycledViews(SlideAdapter.TYPE_2, 4)
        recyclerView.recycledViewPool.setMaxRecycledViews(SlideAdapter.TYPE_1, 4)
        recyclerView.layoutManager = PagerLayoutManager(12) {
            when (it) {
                37 -> SlideAdapter.TYPE_1
                in 0..1 -> SlideAdapter.TYPE_2
                in 18..20 -> SlideAdapter.TYPE_3
                in 46..51 -> SlideAdapter.TYPE_6
                in 56..58 -> SlideAdapter.TYPE_3
                else -> SlideAdapter.TYPE_4
            }
        }
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }
}