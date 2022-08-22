package com.abelhu.androidstudy.ui.main.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abelhu.androidstudy.databinding.FragmentSendBinding

class SendFragment : Fragment() {

    companion object {
        val Tag = SendFragment::class.simpleName
    }

    private lateinit var binding: FragmentSendBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSendBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = SendAdapter()
    }
}