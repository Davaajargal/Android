package mn.cyborg.mgljpndic.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mn.cyborg.mgljpndic.model.SavedWord

@Dao
interface SavedWordDao {
    @Query("SELECT * FROM savedword ORDER BY ID")
    fun loadAllWord(): List<SavedWord>

    @Query("SELECT * FROM savedword WHERE id = :id")
    fun loadWord(id: Int): SavedWord

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(person: SavedWord?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWords(person: List<SavedWord>?)

    @Query("SELECT * FROM savedword ORDER BY id DESC LIMIT 1")
    fun lastWordId(): SavedWord?

    @Query("DELETE FROM savedword where id = :id")
    fun deleteSavedWord(id: Int)

}