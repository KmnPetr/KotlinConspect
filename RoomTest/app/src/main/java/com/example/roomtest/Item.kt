package com.example.roomtest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//это энтити "сущность"
@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,//потом руум сам исправит нал на номер
    @ColumnInfo(name = "word")
    var word:String,
    @ColumnInfo(name = "transcription")
    var transcription:String?,
    @ColumnInfo(name = "translation")
    var translation:String?,
)
