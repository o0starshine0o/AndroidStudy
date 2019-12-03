package com.abelhu.androidstudy.ui.main.home

import android.os.Bundle
import androidx.lifecycle.*

class HomeViewModel(private var count:Int = 0) : ViewModel(), LifecycleObserver {
    companion object {
        const val COUNT_KEY = "COUNT_KEY"
    }
    val text = MutableLiveData<String>().apply { value = "This is home Fragment" }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(){
        text.value = "This is home Fragment:${++count}"
    }

    fun saveState(outState: Bundle) {
        outState.putInt(COUNT_KEY, count)
    }

    fun restoreState(inState: Bundle?) {
        inState?.let { count = inState.getInt(COUNT_KEY) }
    }
}