package com.abelhu.androidstudy.ui.main.tools

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ToolsViewModel : ViewModel() {
    val fragmentInfo = MutableLiveData<String>().apply { value = "This is RIGHT Fragment" }
    val baseStation = MutableLiveData<String>().apply { }
}