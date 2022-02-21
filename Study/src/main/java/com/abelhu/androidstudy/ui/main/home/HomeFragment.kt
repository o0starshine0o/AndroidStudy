package com.abelhu.androidstudy.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abelhu.androidstudy.databinding.FragmentHomeBinding
import com.abelhu.androidstudy.ui.main.activity.mode.LaunchModeActivity
import com.protostar.download.DownloadManager

class HomeFragment : Fragment() {

    private val homeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }
    private val url = "/shadow/plugin/advertisement-debug-plugin-copy.zip"
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.restoreState(savedInstanceState)
        lifecycle.addObserver(homeViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.apply {
            homeViewModel.text.observe(viewLifecycleOwner, Observer { textHome.text = it })
            homeViewModel.progress.observe(viewLifecycleOwner, Observer { progress.progress = it })
            // 点击穿透相关
            click.setOnClickListener { Toast.makeText(context, "click", Toast.LENGTH_LONG).show() }
            // 启动模式相关
            startLaunchModeActivity.setOnClickListener {
                startActivity(Intent(context, LaunchModeActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
            }
            // 下载相关
            download.setOnClickListener {
                DownloadManager.download(url, "${context?.filesDir}/plugin.zip") { _, read, length, _ ->
                    homeViewModel.progress.postValue((100 * read / length).toInt())
                }
            }
            pause.setOnClickListener { DownloadManager.pause(url) }
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        homeViewModel.saveState(outState)
    }
}
