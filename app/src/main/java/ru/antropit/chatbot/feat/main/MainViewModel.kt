package ru.antropit.chatbot.feat.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.antropit.chatbot.util.singleArgViewModelFactory

class MainViewModel(private val _message : MutableLiveData<String>) : ViewModel() {
    companion object {
        /**
         * Factory for creating [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModel)
    }

    val message: LiveData<String>
        get() = _message
}
