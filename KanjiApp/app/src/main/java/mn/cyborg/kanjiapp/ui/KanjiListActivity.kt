package mn.cyborg.kanjiapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.android.synthetic.main.activity_kanji_list.*
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.adapter.KanjiAdapter
import mn.cyborg.kanjiapp.database.AppDatabase
import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.util.GridItemDecoration

class KanjiListActivity : AppCompatActivity() {

    var lvlId = 0
    lateinit var mAdView : AdView

    private var mDb: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kanji_list)
        mDb =  AppDatabase.getInstance(applicationContext)
        lvlId = intent.getIntExtra("lvlId", 5)
        initView()

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)

        val testDeviceIds = listOf("F08D34BD7FFD2B11EE1D67F75CAE8902")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }

    private fun initView() {

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "N$lvlId"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        kanji_rec.layoutManager = GridLayoutManager(this, 4)

        //This will for default android divider
        kanji_rec.addItemDecoration(GridItemDecoration(10, 2))

        val movieListAdapter = KanjiAdapter(applicationContext
        )
        kanji_rec.adapter = movieListAdapter
        movieListAdapter.setMovieList(generateDummyData())
        movieListAdapter.notifyDataSetChanged()
    }

    private fun generateDummyData(): List<Kanji> {
        val listOfKanji = mutableListOf<Kanji>()
        mDb?.kanjiDao()?.loadAllKanji(lvlId)?.let { listOfKanji.addAll(it) }
        return listOfKanji
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.lisy_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_cart -> {
            val flashInt = Intent(applicationContext, FlashCartSettingActivity::class.java)
            flashInt.putExtra("lvlid", lvlId)
            startActivity(flashInt)
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
        super.onDestroy()
    }

}