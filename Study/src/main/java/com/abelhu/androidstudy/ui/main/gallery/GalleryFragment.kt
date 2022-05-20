package com.abelhu.androidstudy.ui.main.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.abelhu.androidstudy.databinding.FragmentGalleryBinding
import com.abelhu.androidstudy.hilt.adapter.HiltAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Hilt 可以为带有 @AndroidEntryPoint 注释的其他 Android 类提供依赖项
 */
@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private val viewModel by lazy { ViewModelProvider(this).get(GalleryViewModel::class.java) }

    private lateinit var binding: FragmentGalleryBinding

    // 因为adapter是依赖fragment的,所以没有办法在这里注入adapter
    private val adapter = HiltAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentGalleryBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.text.observe(viewLifecycleOwner) { binding.textGallery.text = it }

        binding.recyclerView.adapter = adapter
    }
}