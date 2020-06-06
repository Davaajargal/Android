package mn.cyborg.kanjiapp.model
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Kanji")
class Kanji {

//    constructor(id: Int, kanji: String, stroke: Int, onyomi: String, kunyomi: String, eng: String) {
//        this.id = id
//        this.kanji = kanji
//        this.stroke = stroke
//        this.onyomi = onyomi
//        this.kunyomi = kunyomi
//        this.eng = eng
//    }

    @PrimaryKey
    var id: Int = 0
    var kanji: String = ""
    var stroke: String = ""
    var onyomi: String = ""
    var kunyomi: String = ""
    var desc: String = ""
    var eng: String = ""
    var lvl: Int = 0
    var createddate: String = ""
    var updateddate: String = ""

}
