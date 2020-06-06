package mn.cyborg.mgljpndic.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import mn.cyborg.mgljpndic.MainActivity
import mn.cyborg.mgljpndic.R
import mn.cyborg.mgljpndic.database.AppDatabase
import mn.cyborg.mgljpndic.http.ApiClient
import mn.cyborg.mgljpndic.model.Word
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {

    private var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        mDb = AppDatabase.getInstance(applicationContext)
        if (mDb?.wordDao()?.loadAllWord()?.size != 0) {
            val lastID: Int? = mDb?.wordDao()?.lastWordId()?.id

            if (lastID != null) {
                getData(lastID)
            } else {
                getData(0)
            }
        } else {
            getData(0)
        }

    }

    private fun getData(lastID: Int) {

        // Log.e("SplashScreenActivity", "last id : $lastID")

        val call: Call<List<Word>> = ApiClient.getClient.getPhotos(lastID)
        call.enqueue(object : Callback<List<Word>> {

            override fun onResponse(call: Call<List<Word>>?, response: Response<List<Word>>?) {
                mDb?.wordDao()?.insertWords(response!!.body()!!)
                val mInt = Intent(applicationContext, MainActivity::class.java)
                startActivity(mInt)
                finish()
            }

            override fun onFailure(call: Call<List<Word>>?, t: Throwable?) {
                t?.printStackTrace()

                if (mDb?.wordDao()?.loadAllWord()?.size != 0) {
                    val mInt = Intent(applicationContext, MainActivity::class.java)
                    startActivity(mInt)
                    finish()
                } else {
                    finish()
                }
            }

        })
    }
}