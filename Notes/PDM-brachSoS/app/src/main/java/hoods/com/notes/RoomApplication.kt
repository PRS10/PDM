package hoods.com.notes

import android.app.Application
import androidx.room.Room
import hoods.com.notes.data.room.AppDatabase

class RoomApplication:Application() {
    companion object{
        lateinit var db : AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "Favorites"
        ).build()

    }
}