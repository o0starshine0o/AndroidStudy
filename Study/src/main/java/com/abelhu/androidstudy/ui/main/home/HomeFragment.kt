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
import com.abelhu.androidstudy.R
import com.abelhu.androidstudy.ui.main.activity.mode.LaunchModeActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private val homeViewModel by lazy { ViewModelProvider(this).get(HomeViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.restoreState(savedInstanceState)
        lifecycle.addObserver(homeViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            homeViewModel.text.observe(this@HomeFragment, Observer { textHome.text = it })
            click.setOnClickListener { Toast.makeText(context, "click", Toast.LENGTH_LONG).show() }
            startLaunchModeActivity.setOnClickListener {
                startActivity(Intent(context, LaunchModeActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        homeViewModel.saveState(outState)
    }
}
