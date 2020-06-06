package mn.cyborg.mgljpndic.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mn.cyborg.mgljpndic.model.Word

@Dao
interface WordDao {
    @Query("SELECT * FROM word ORDER BY ID")
    fun loadAllWord(): List<Word>

    @Query("SELECT * FROM word WHERE id = :id")
    fun loadWord(id: Int): Word

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(person: Word?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWords(person: List<Word>?)

    @Query("SELECT * FROM word ORDER BY id DESC LIMIT 1")
    fun lastWordId(): Word?

}