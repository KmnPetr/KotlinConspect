package com.example.adminapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.adminapp.model.models.Word

class ViewModel:ViewModel() {
    var token= MutableLiveData<String>()

    var list=MutableLiveData<ArrayList<Word>>()

}