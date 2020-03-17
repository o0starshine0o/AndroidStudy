package com.abelhu.androidstudy.ui.main.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToolsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is BUG Fragment"
    }
    val text: LiveData<String> = _text
}