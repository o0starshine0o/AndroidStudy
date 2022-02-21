package com.abelhu.androidstudy.ui.main.activity.mode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abelhu.androidstudy.databinding.ActivityLaunchModeBinding
import com.google.android.material.snackbar.Snackbar

class LaunchModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
