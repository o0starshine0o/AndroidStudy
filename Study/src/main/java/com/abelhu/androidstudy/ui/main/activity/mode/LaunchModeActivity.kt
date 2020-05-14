package com.abelhu.androidstudy.ui.main.activity.mode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abelhu.androidstudy.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_launch_mode.*

class LaunchModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_mode)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
