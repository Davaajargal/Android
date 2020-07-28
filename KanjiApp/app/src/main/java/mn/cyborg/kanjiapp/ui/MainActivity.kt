package mn.cyborg.kanjiapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.database.AppDatabase
import mn.cyborg.kanjiapp.http.ApiClient
import mn.cyborg.kanjiapp.model.Kanji
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    // private val TAG = "MainActivity"
    private lateinit var mAdView: AdView
    private var mDb: AppDatabase? = null
    private val compositeSubscription = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDb = AppDatabase.getInstance(applicationContext)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)

        val testDeviceIds = listOf("F08D34BD7FFD2B11EE1D67F75CAE8902")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.visibility = View.GONE
        fab.setOnClickListener {

//            val intent = Intent(applicationContext, NewWordActivity::class.java)
//            startActivity(intent)

        }

        swiperefresh.setOnRefreshListener {
            getData()
        }

        game_card.setOnClickListener {

        }

        seach_card.setOnClickListener {
            val searchintent = Intent(applicationContext, SearchActivity::class.java)
            startActivity(searchintent)
        }

        n5_card.setOnClickListener {
            val n5intent = Intent(applicationContext, KanjiListActivity::class.java)
            n5intent.putExtra("lvlId", 5)
            startActivity(n5intent)
        }

        n4_card.setOnClickListener {
            val n5intent = Intent(applicationContext, KanjiListActivity::class.java)
            n5intent.putExtra("lvlId", 4)
            startActivity(n5intent)
        }

        n3_card.setOnClickListener {
            val n5intent = Intent(applicationContext, KanjiListActivity::class.java)
            n5intent.putExtra("lvlId", 3)
            startActivity(n5intent)
        }

        n2_card.setOnClickListener {
            val n5intent = Intent(applicationContext, KanjiListActivity::class.java)
            n5intent.putExtra("lvlId", 2)
            startActivity(n5intent)
        }

        n1_card.setOnClickListener {
            val n5intent = Intent(applicationContext, KanjiListActivity::class.java)
            n5intent.putExtra("lvlId", 1)
            startActivity(n5intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_cart -> {
            getData()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    public override fun onPause() {
        mAdView.pause()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        mAdView.resume()
    }

    public override fun onDestroy() {
        mAdView.destroy()
        compositeSubscription.clear()
        super.onDestroy()
    }

    private fun getData() {

        // Log.e("SplashScreenActivity", "last id : $lastID")
        swiperefresh.isRefreshing = true

        compositeSubscription.clear()
        compositeSubscription.add(
            ApiClient.apiClient.getPhotos(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                mDb?.kanjiDao()?.resetKanji()
                mDb?.kanjiDao()?.insertKanjis(it)

                }
                .doOnError {
                Toast.makeText(
                    applicationContext,
                    "Өгөгдлийг шинэчлэхэд алдаа гарлаа!!!",
                    Toast.LENGTH_SHORT
                ).show()
                }
                .doOnCompleted {

                    Toast.makeText(
                        applicationContext,
                        "Өгөгдлийг шинэчилж дууслаа!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    swiperefresh.isRefreshing = false

                }
                .subscribe())



//        val call: Call<List<Kanji>> = ApiClient.getClient.getPhotos(0)
//        call.enqueue(object : Callback<List<Kanji>> {
//
//            override fun onResponse(call: Call<List<Kanji>>?, response: Response<List<Kanji>>?) {
//                mDb?.kanjiDao()?.resetKanji()
//                mDb?.kanjiDao()?.insertKanjis(response!!.body()!!)
//                Toast.makeText(
//                    applicationContext,
//                    "Өгөгдлийг шинэчилж дууслаа!!!",
//                    Toast.LENGTH_SHORT
//                ).show()
//                swiperefresh.isRefreshing = false
//            }
//
//            override fun onFailure(call: Call<List<Kanji>>?, t: Throwable?) {
//                t?.printStackTrace()
//                Toast.makeText(
//                    applicationContext,
//                    "Өгөгдлийг шинэчлэхэд алдаа гарлаа!!!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//        })
    }

}
