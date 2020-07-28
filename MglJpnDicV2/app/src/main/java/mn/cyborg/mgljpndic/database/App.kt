package mn.cyborg.mgljpndic.database

import android.app.Application


class App : Application() {
    private val appRunning = false
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this) //--AppDatabase_Impl does not exist
    }
}