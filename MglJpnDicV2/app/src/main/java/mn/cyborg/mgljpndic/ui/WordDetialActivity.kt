package mn.cyborg.mgljpndic.ui

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import kotlinx.android.synthetic.main.activity_word_detial.*
import mn.cyborg.mgljpndic.R
import mn.cyborg.mgljpndic.database.AppDatabase
import mn.cyborg.mgljpndic.model.SavedWord
import java.util.*

class WordDetialActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var mDb: AppDatabase? = null
    lateinit var mAdView : AdView
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wordID = intent.getIntExtra("wordId", 0)

        setContentView(R.layout.activity_word_detial)

        mDb = AppDatabase.getInstance(applicationContext)
        tts = TextToSpeech(this, this)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val testDeviceIds = listOf("F08D34BD7FFD2B11EE1D67F75CAE8902")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Дэлгэрэнгүй"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        // HomeFragment


        val word = mDb?.wordDao()?.loadWord(wordID)

        kanjiTv.text = word?.kanji
        hiraTv.text = word?.hira
        dictTv.text = word?.dict
        jpnsLvlTv.text = word?.jpnslvl
        typeTv.text = word?.type
        engTv.text = word?.englsh

        if(!word?.type.isNullOrEmpty()){
            val arr = resources.getStringArray(R.array.types)
            typeTv.text = arr[word?.type?.toInt()!!]
        }else{
            typeTv.text ="Миннано нихонго"
        }

        val savedWord = mDb?.savedWordDao()?.loadWord(wordID)

        if(savedWord != null){
            savewordbtn.text = "Хадгалагдсан"
        }else{
            savewordbtn.text = "Хадгалах"
        }

        speackBtn.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(word?.kanji, TextToSpeech.QUEUE_FLUSH, null,"")
            }

        }

        savewordbtn.setOnClickListener {

            if(savedWord != null){
                mDb?.savedWordDao()?.deleteSavedWord(word?.id!!)
                savewordbtn.text = "Хадгалах"
            }else{
                var savedWord = SavedWord()
                savedWord.id = word?.id!!
                savedWord.kanji = word.kanji
                savedWord.hira = word.hira
                savedWord.dict = word.dict
                savedWord.jpnslvl = word.jpnslvl
                savedWord.wordtype = word.type
                savedWord.wordtitle = word.typelvl
                savedWord.englsh = word.englsh

                mDb?.savedWordDao()?.insertWord(savedWord)

                Toast.makeText(applicationContext, "Хадгалагдлаа", Toast.LENGTH_SHORT).show()

                savewordbtn.text = "Хадгалагдсан"
            }
        }

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