package hoods.com.notes.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hoods.com.notes.data.room.models.Fav
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM Favorites WHERE userID = :userID AND noteID = :noteID")
    fun getFavByUserAndNote(userID: String, noteID: String): Flow<Fav?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fav: Fav)

    @Update
    fun update(fav: Fav)
}