package com.abelhu.androidstudy.ui.main.slideshow

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abelhu.androidstudy.R
import com.abelhu.androidstudy.extension.tag
import kotlinx.android.synthetic.main.item_icon.view.*
import kotlin.random.Random

class SlideAdapter : RecyclerView.Adapter<SlideAdapter.SlideHolder>() {
    companion object {
        const val TYPE_4 = 3
        const val TYPE_2 = 6
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        Log.i(tag(), "onCreateViewHolder with type: $viewType")
        return SlideHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_icon, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0, 1 -> TYPE_2
            else -> TYPE_4
        }
    }

    override fun getItemCount(): Int {
        return 200
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {
        Log.i(tag(), "onBindViewHolder with position: $position")
        holder.initHolder(position)
    }

    override fun onViewRecycled(holder: SlideHolder) {
        super.onViewRecycled(holder)
        Log.i(tag(), "onViewRecycled with position: ${holder.recycleHolder()}")
    }

    class SlideHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var index = 0
        fun initHolder(position: Int) {
            index = position
            itemView.iconView.setBackgroundColor(Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
            itemView.nameView.text = position.toString()
            itemView.tag = position
            Log.i(tag(), "init holder: $position")
        }

        fun recycleHolder() = index
    }
}