package com.example.conferenceroom.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.conferenceroom.data.DateConverter
import com.example.conferenceroom.data.model.Slots

@Database(entities = [Slots::class], version = 1, exportSchema = false)
@TypeConverters(
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): SlotsDao?
}