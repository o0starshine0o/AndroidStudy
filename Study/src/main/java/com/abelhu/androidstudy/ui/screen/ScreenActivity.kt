package com.abelhu.androidstudy.ui.screen

import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import com.abelhu.androidstudy.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_screen.*

class ScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.rootView, ScreenFragment())
        transaction.commit()
    }

}
