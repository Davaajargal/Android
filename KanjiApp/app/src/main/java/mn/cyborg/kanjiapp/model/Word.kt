package mn.cyborg.kanjiapp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Word")
class Word {

    @PrimaryKey
    var id: Int = 0
    var kanji: String = ""
    var hira: String = ""
    var dict: String = ""
    var jpnslvl: String = ""
    var type: String = ""
    var eng: String = ""
    var typelvl: String = ""


}