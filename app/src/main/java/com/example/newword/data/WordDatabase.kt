package com.example.newword.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var Instance: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, WordDatabase::class.java, "word_database").build().also{
                    Instance = it
                }
            }
        }
    }
}
