package com.abelhu.androidstudy.ui.send

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.abelhu.androidstudy.R
import com.abelhu.androidstudy.message.SendMessage
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_send.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SendFragment : Fragment() {

    private lateinit var sendViewModel: SendViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sendViewModel = ViewModelProviders.of(this).get(SendViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        sendViewModel.text.observe(this, Observer { textSend.text = it })
        return root
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    fun sendMessage(message:SendMessage){
        Snackbar.make(rootView, "get message from main", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }
}