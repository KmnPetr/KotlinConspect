package com.example.adminapp.model.dto

data class ValidationExeptionResponseDTO(
    val message:String,
    val validationErrors:Map<String,String>
)