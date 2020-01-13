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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideHolder {
        return SlideHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_icon, parent, false))
    }

    override fun getItemCount(): Int {
        return 200
    }

    override fun onBindViewHolder(holder: SlideHolder, position: Int) {
        holder.initHolder(position)
    }

    class SlideHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun initHolder(position: Int) {
            itemView.iconView.setBackgroundColor(Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)))
            itemView.nameView.text = position.toString()
            itemView.tag = position
            Log.i(tag(), "init holder:$position")
        }
    }
}