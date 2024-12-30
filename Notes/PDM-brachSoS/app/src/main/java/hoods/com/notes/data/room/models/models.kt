package hoods.com.notes.data.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class Fav(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val userID: String,
        val noteID: String,
        val favorite: Boolean = false
)
