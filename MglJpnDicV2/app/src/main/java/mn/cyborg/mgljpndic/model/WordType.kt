package mn.cyborg.mgljpndic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WordType")
class WordType{

    @PrimaryKey
    var id: Int = 0
    var name: String = ""

}