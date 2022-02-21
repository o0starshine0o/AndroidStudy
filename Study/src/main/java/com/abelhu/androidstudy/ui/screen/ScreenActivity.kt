package com.abelhu.androidstudy.ui.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abelhu.androidstudy.R
import com.abelhu.androidstudy.databinding.ActivityScreenBinding
import com.google.android.material.snackbar.Snackbar

class ScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.rootView, ScreenFragment())
        transaction.commit()
    }

}
