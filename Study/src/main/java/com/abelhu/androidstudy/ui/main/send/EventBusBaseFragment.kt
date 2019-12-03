package com.abelhu.androidstudy.ui.main.send

import android.util.Log
import androidx.fragment.app.Fragment
import com.abelhu.androidstudy.message.SendMessage
import org.greenrobot.eventbus.Subscribe

open class EventBusBaseFragment : Fragment() {

    companion object {
        val Tag = EventBusBaseFragment::class.simpleName
    }

    @Subscribe
    open fun subscribeMessage0(message: SendMessage) {
        Log.d(Tag, "EventBusBaseFragment subscribeMessage0")
    }

    @Subscribe
    open fun subscribeMessage1(message: SendMessage) {
        Log.d(Tag, "EventBusBaseFragment subscribeMessage1")
    }
}