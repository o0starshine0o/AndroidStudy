package com.abelhu.androidstudy.ui.main.share

import android.annotation.SuppressLint
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abelhu.androidstudy.databinding.FragmentShareBinding
import com.qicode.extension.TAG

class ShareFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel
    private lateinit var binding: FragmentShareBinding

    private var toast: Toast? = null

    @SuppressLint("ShowToast")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        shareViewModel = ViewModelProvider(this).get(ShareViewModel::class.java)
        binding = FragmentShareBinding.inflate(layoutInflater)
        shareViewModel.text.observe(viewLifecycleOwner, Observer { binding.textShare.text = it })

        binding.toastView.setOnClickListener {
            if (toast == null) {
                toast = Toast.makeText(it.context, "toast test", Toast.LENGTH_LONG)
            } else {
                toast?.setText("toast test crash")
            }
            toast?.show()
            Log.i(TAG(), "toast show: ${System.currentTimeMillis()}")
            Thread.sleep(1980)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.surface.setZOrderOnTop(true)
        binding.surface.holder.setFormat(PixelFormat.TRANSPARENT)
//        binding.surface.setZOrderMediaOverlay(true)
    }
}