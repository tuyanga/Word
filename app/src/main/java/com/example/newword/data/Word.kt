package com.example.newword.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val foreign: String,
    val mongolian: String
)