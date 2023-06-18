package com.example.roomtest

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Insert//запись
    fun insertItem(item: Item)
    @Query("SELECT*FROM items")//запрос
    fun getAllItem(): Flow<List<Item>> //вернет список итемов. Flow автоматизирует проверку изменений в базе
}