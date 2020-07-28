package mn.cyborg.mgljpndic.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "SavedWord")
class SavedWord {

    @PrimaryKey
    var id: Int = 0
    var kanji: String = ""
    var hira: String = ""
    var dict: String = ""
    var jpnslvl: String = ""
    var wordtype: String = ""
    var englsh: String = ""
    var wordtitle: String = ""

}