package com.abelhu.androidstudy.ui.main.send

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.abelhu.androidstudy.databinding.FragmentSendBinding
import com.abelhu.androidstudy.message.SendMessage
import com.google.android.material.snackbar.Snackbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SendFragment : EventBusBaseFragment() {

    companion object {
        val Tag = SendFragment::class.simpleName
    }

    private lateinit var sendViewModel: SendViewModel
    private lateinit var binding: FragmentSendBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sendViewModel = ViewModelProvider(this).get(SendViewModel::class.java)
        binding = FragmentSendBinding.inflate(layoutInflater)
        sendViewModel.text.observe(viewLifecycleOwner) { binding.textSend.text = it }
        return binding.root
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
    override fun subscribeMessage0(message: SendMessage) {
        Snackbar.make(binding.rootView, "get message from main", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        Log.d(Tag, "subscribeMessage0")
    }
    // there is a bug in EventBus: https://github.com/greenrobot/EventBus/issues/539
    // but i fix this bug: https://github.com/greenrobot/EventBus/pull/615
//    @Subscribe(threadMode = ThreadMode.POSTING)
//    override fun subscribeMessage1(message:SendMessage){
//        Log.d(Tag, "subscribeMessage1")
//    }
}