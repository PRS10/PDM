package hoods.com.notes.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hoods.com.notes.data.room.models.Fav

@Database(
        entities = [Fav::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao


}



