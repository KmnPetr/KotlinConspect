package com.example.testretrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {
    val token=MutableLiveData<String>()//при изменении этой переменной запустится обсервер
}