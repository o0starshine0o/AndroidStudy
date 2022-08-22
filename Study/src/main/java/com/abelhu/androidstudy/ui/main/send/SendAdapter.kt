package com.abelhu.androidstudy.ui.main.send

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abelhu.androidstudy.databinding.ItemSendViewHolderBinding


class SendAdapter : RecyclerView.Adapter<SendViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSendViewHolderBinding.inflate(inflater, parent, false)
//        binding.textView.movementMethod = ScrollingMovementMethod()
        binding.textView.movementMethod = LocalLinkMovementMethod.instance // 不影响
        binding.textView.setTextIsSelectable(false) // 这个不影响
        return SendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SendViewHolder, position: Int) {
    }

    override fun getItemCount() = 10
}