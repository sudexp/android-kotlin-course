package com.example.fragmentandviewmodel.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    // set _text
    private val _text = MutableLiveData<String>().apply {
        value = "This is my hardcoded text string here!"
    }

    //get
    val text: LiveData<String> = _text
}
