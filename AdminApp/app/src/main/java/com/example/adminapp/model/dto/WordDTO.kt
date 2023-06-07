package com.example.adminapp.model.dto

import java.time.LocalDateTime

data class WordDTO(
    val id: Int?,
    val foreignWord:String,
    val transcription:String,
    val translation:String,
    val hasVoise: String?,
    val hasImage: String?,
    val groupWord:String,
    val updatedAt: LocalDateTime?
)