package mn.cyborg.kanjiapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.android.synthetic.main.activity_kanji_detail.*
import kotlinx.android.synthetic.main.activity_kanji_detail.speackBtn
import kotlinx.android.synthetic.main.activity_word_detial.*
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.adapter.WordAdapter
import mn.cyborg.kanjiapp.adapter.WordViewHolder
import mn.cyborg.kanjiapp.database.AppDatabase
import mn.cyborg.kanjiapp.model.Word
import java.util.*

class KanjiDetialActivity : AppCompatActivity(), WordViewHolder.ItemClickListener, TextToSpeech.OnInitListener {

    private var mDb: AppDatabase? = null
    var id = 0
    var lvlid = 0
    lateinit var mAdView: AdView
    private var tts: TextToSpeech? = null

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var wordsList: ArrayList<Word> = ArrayList()
    private var recView: RecyclerView? = null

    private var adapter: WordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDb = AppDatabase.getInstance(applicationContext)
        setContentView(R.layout.activity_kanji_detail)
        tts = TextToSpeech(this, this)

        id = intent.getIntExtra("kanjiId", 0)
        lvlid = intent.getIntExtra("lvlid", 0)
        initUI()
        val kanjiModel = mDb?.kanjiDao()?.loadKanji(id)

        val kanjiStr = kanjiModel?.kanji
        det_kanji.text = kanjiStr
        det_on_yomi.text = kanjiModel?.onyomi
        det_kun_yomi.text = kanjiModel?.kunyomi
        det_desc.text = kanjiModel?.desc
        det_kanji.text = kanjiModel?.kanji
        det_eng.text = kanjiModel?.eng

        if (kanjiStr != null) {
            mDb?.wordDao()?.loadWordByKanji(kanjiStr)?.let { wordsList.addAll(it) }
        }

        speackBtn.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(kanjiModel?.kanji, TextToSpeech.QUEUE_FLUSH, null,"")
            }

        }

        adapter?.notifyDataSetChanged()

        flashcartbtn.setOnClickListener {
            val flashInt = Intent(applicationContext, FlashCartSettingActivity::class.java)
            flashInt.putExtra("lvlid", lvlid)
            startActivity(flashInt)
        }

    }

    fun initUI() {

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "N$lvlid"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)

        val testDeviceIds = listOf("F08D34BD7FFD2B11EE1D67F75CAE8902")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        recView = findViewById(R.id.word_list_rc)
        adapter = WordAdapter(applicationContext, this, wordsList)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        recView?.layoutManager = linearLayoutManager
        recView?.adapter = adapter
        recView?.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

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
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    override fun onItemClick(view: View, position: Int) {
        // Log.e("data", wordsList[position].kanji)
        val word = wordsList[position]
        val intent = Intent(applicationContext, WordDetialActivity::class.java)
        intent.putExtra("wordId", word.id)
        startActivity(intent)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.JAPANESE)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                speackBtn!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

}