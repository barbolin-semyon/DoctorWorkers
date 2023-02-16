package com.example.doctorworkers.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

private val _messageError = MutableLiveData("")
val messageError: LiveData<String>
    get() = _messageError

fun showMessageError(message: String = "Произошла ошибка, попробуйте снова") {
    _messageError.value = message
}