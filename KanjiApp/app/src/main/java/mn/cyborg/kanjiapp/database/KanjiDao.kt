package mn.cyborg.kanjiapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mn.cyborg.kanjiapp.model.Kanji

@Dao
interface KanjiDao {

    @Query("SELECT * FROM kanji ORDER BY ID")
    fun loadAllKanjis(): List<Kanji>

    @Query("SELECT * FROM kanji WHERE lvl = :lvlid ORDER BY ID")
    fun loadAllKanji(lvlid: Int): List<Kanji>

    @Query("SELECT * FROM kanji WHERE id = :id")
    fun loadKanji(id: Int): Kanji

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKanji(person: Kanji?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKanjis(person: List<Kanji>?)

    @Query("SELECT * FROM kanji ORDER BY id DESC LIMIT 1")
    fun lastKanjiId(): Kanji?

    @Query("DELETE FROM kanji")
    fun resetKanji()

}