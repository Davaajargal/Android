package mn.cyborg.kanjiapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.model.Word


@Database(entities = [Kanji::class, Word::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kanjiDao(): KanjiDao?
    abstract fun wordDao(): WordDao?

    companion object {
        private val LOG_TAG = AppDatabase::class.java.simpleName
        private val LOCK = Any()
        private const val DATABASE_NAME = "kanji"
        private var sInstance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    ).allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return sInstance
        }
    }
}