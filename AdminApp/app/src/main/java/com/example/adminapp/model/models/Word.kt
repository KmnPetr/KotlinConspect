package com.example.adminapp.model.models

import java.time.LocalDateTime

data class Word(
    val id: Int?,
    val foreignWord:String,
    val transcription:String,
    val translation:String,
    val hasVoise: String?,
    val hasImage: String?,
    val groupWord:String,
    val updatedAt: LocalDateTime?
    ){
    override fun toString(): String {
        return "Word ($id, '$foreignWord', $transcription, '$translation', $hasVoise, $hasImage, $groupWord, $updatedAt)"
    }
}
