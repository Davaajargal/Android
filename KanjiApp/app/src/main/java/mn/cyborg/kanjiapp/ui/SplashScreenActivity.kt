package mn.cyborg.kanjiapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.database.AppDatabase
import mn.cyborg.kanjiapp.http.ApiClient
import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.model.Word
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class SplashScreenActivity : AppCompatActivity() {

    private var mDb: AppDatabase? = null
    private val compositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        mDb = AppDatabase.getInstance(applicationContext)

        if (mDb?.kanjiDao()?.loadAllKanji(5)?.size != 0) {
            val lastID: Int? = mDb?.kanjiDao()?.lastKanjiId()?.id

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

        compositeSubscription.clear()
        compositeSubscription.add(
            ApiClient.apiClient.getPhotos(lastID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {

                    //Log.d(TAG, "response=$it")

                    mDb?.kanjiDao()?.insertKanjis(it!!)



                }
                .doOnError {

                    if (mDb?.kanjiDao()?.loadAllKanji(5)?.size != 0) {
                        val mInt = Intent(applicationContext, MainActivity::class.java)
                        startActivity(mInt)
                        finish()
                    } else {
                        finish()
                    }

                }
                .doOnCompleted {

                    if (mDb?.wordDao()?.loadAllWord()?.size != 0) {
                        val lastWordID: Int? = mDb?.wordDao()?.lastWordId()?.id

                        if (lastWordID != null) {
                            getWordData(lastWordID)
                        } else {
                            getWordData(0)
                        }
                    } else {
                        getWordData(0)
                    }

                }
                .subscribe())

//        val call: Call<List<Kanji>> = ApiClient.getClient.getPhotos(lastID)
//        call.enqueue(object : Callback<List<Kanji>> {
//
//            override fun onResponse(call: Call<List<Kanji>>?, response: Response<List<Kanji>>?) {
//                mDb?.kanjiDao()?.insertKanjis(response!!.body()!!)
//
//                if (mDb?.wordDao()?.loadAllWord()?.size != 0) {
//                    val lastWordID: Int? = mDb?.wordDao()?.lastWordId()?.id
//
//                    if (lastWordID != null) {
//                        getWordData(lastWordID)
//                    } else {
//                        getWordData(0)
//                    }
//                } else {
//                    getWordData(0)
//                }
//
//            }
//
//            override fun onFailure(call: Call<List<Kanji>>?, t: Throwable?) {
//                t?.printStackTrace()
//
//                if (mDb?.kanjiDao()?.loadAllKanji(5)?.size != 0) {
//                    val mInt = Intent(applicationContext, MainActivity::class.java)
//                    startActivity(mInt)
//                    finish()
//                } else {
//                    finish()
//                }
//            }
//
//        })
    }

    private fun getWordData(lastID: Int) {

        // Log.e("SplashScreenActivity", "last id : $lastID")


        compositeSubscription.clear()
        compositeSubscription.add(
            ApiClient.apiClient.getWords(lastID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    mDb?.wordDao()?.insertWords(it)

                }
                .doOnError {
                    if (mDb?.kanjiDao()?.loadAllKanji(5)?.size != 0) {
                        val mInt = Intent(applicationContext, MainActivity::class.java)
                        startActivity(mInt)
                        finish()
                    } else {
                        finish()
                    }
                }
                .doOnCompleted {
                    val mInt = Intent(applicationContext, MainActivity::class.java)
                    startActivity(mInt)
                    finish()
                }
                .subscribe())





//        val call: Call<List<Word>> = ApiClient.getClient.getWords(lastID)
//        call.enqueue(object : Callback<List<Word>> {
//
//            override fun onResponse(call: Call<List<Word>>?, response: Response<List<Word>>?) {
//                mDb?.wordDao()?.insertWords(response!!.body()!!)
//                val mInt = Intent(applicationContext, MainActivity::class.java)
//                startActivity(mInt)
//                finish()
//            }
//
//            override fun onFailure(call: Call<List<Word>>?, t: Throwable?) {
//                t?.printStackTrace()
//
//                if (mDb?.kanjiDao()?.loadAllKanji(5)?.size != 0) {
//                    val mInt = Intent(applicationContext, MainActivity::class.java)
//                    startActivity(mInt)
//                    finish()
//                } else {
//                    finish()
//                }
//            }
//
//        })
    }

    override fun onDestroy() {
        compositeSubscription.clear()
        super.onDestroy()
    }

}