package com.abelhu.androidstudy.ui.main.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.abelhu.androidstudy.R
import com.abelhu.layout.PagerGridLayoutManager
import com.abelhu.layout.PagerLinearLayoutManager
import kotlinx.android.synthetic.main.fragment_slideshow.*

class SlideFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slideshow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.adapter = SlideAdapter()
//        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = PagerLinearLayoutManager(context)
//        recyclerView.layoutManager = GridLayoutManager(context, 12, RecyclerView.HORIZONTAL, false)
//        (recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return when (position) {ad
//                    0, 1 -> 3
//                    else -> 3
//                }
//            }
//        }
        recyclerView.layoutManager = PagerGridLayoutManager(context, 12, RecyclerView.HORIZONTAL, false)
        (recyclerView.layoutManager as PagerGridLayoutManager).spanSizeLookup = object : PagerGridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0, 1 -> 6
                    else -> 3
                }
            }
        }
//        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }
}