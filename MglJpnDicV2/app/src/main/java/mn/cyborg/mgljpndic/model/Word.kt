package mn.cyborg.mgljpndic.model

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
    var englsh: String = ""
    var typelvl: String = ""


}