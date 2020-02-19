package com.abelhu.androidstudy.ui.main.share

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abelhu.androidstudy.R
import com.qicode.extension.TAG
import kotlinx.android.synthetic.main.fragment_share.view.*

class ShareFragment : Fragment() {

    private lateinit var shareViewModel: ShareViewModel

    private var toast: Toast? = null

    @SuppressLint("ShowToast")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        shareViewModel = ViewModelProvider(this).get(ShareViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_share, container, false)
        shareViewModel.text.observe(this, Observer { root.text_share.text = it })

        root.toastView.setOnClickListener {
            if (toast == null) {
                toast = Toast.makeText(it.context, "toast test", Toast.LENGTH_LONG)
            } else {
                toast?.setText("toast test crash")
            }
            toast?.show()
            Log.i(TAG(), "toast show: ${System.currentTimeMillis()}")
            Thread.sleep(1980)
        }
        return root
    }
}