package com.example.roomtest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class/*тут через запятую перечисляем классы таблицы*/], version = 1/*что такое миграции?*/)//передаем как массив потомучто будут еще энтити
abstract class MainDb: RoomDatabase() {
    abstract fun getDAO():DAO//метод возвращает обьект DAO

    companion object{
        fun getDb(context: Context):MainDb{
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "test.db"
            ).build()
        }
    }
}