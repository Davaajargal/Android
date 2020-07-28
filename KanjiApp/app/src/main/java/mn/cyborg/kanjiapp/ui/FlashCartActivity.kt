package mn.cyborg.kanjiapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.adapter.FlashCartAdapter
import mn.cyborg.kanjiapp.database.AppDatabase
import mn.cyborg.kanjiapp.model.Kanji
import kotlin.collections.ArrayList


class FlashCartActivity : AppCompatActivity() {

    val TAG = "FlashCartActivity"

    private lateinit var viewPager: ViewPager
    private var mDb: AppDatabase? = null

    lateinit var mAdView: AdView
    var lvlid = 0
    var lvls = ""
    var types = ""

    private lateinit var pagerAdapter: FlashCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_flash_cart)

        val listOfKanji = mutableListOf<Kanji>()
        lvlid = intent.getIntExtra("lvlid", 0)
        lvls = intent.getStringExtra("lvls") as String
        types = intent.getStringExtra("types") as String
        initUI()

        val lvlsArr = lvls.split(",")
        val typesArr = types.split(",")

        for (x in 0 until lvlsArr.size - 1) {
            mDb?.kanjiDao()?.loadAllKanji(lvlsArr[x].toInt())?.let { listOfKanji.addAll(it) }
        }

        pagerAdapter =
            FlashCartAdapter(supportFragmentManager, listOfKanji as ArrayList<Kanji>, typesArr)
        viewPager.adapter = pagerAdapter

    }

    private fun initUI() {

        //actionbar
        mDb = AppDatabase.getInstance(applicationContext)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "N$lvlid"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        viewPager = findViewById(R.id.viewPager)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)

        val testDeviceIds = listOf("F08D34BD7FFD2B11EE1D67F75CAE8902")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
        super.onDestroy()
    }

}