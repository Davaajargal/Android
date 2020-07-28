package mn.cyborg.kanjiapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_kanji_list.*
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.adapter.KanjiSearchAdapter
import mn.cyborg.kanjiapp.database.AppDatabase
import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.util.GridItemDecoration

class SearchActivity : AppCompatActivity() {

    private var mDb: AppDatabase? = null

    private var hanz_list_rv: RecyclerView? = null
    private var seach_et: TextView? = null

    var listOfKanji = mutableListOf<Kanji>()

    var adapter: KanjiSearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        mDb =  AppDatabase.getInstance(applicationContext)

        initView()

        seach_et?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(p0.toString())
            }
        })

    }

    private fun initView() {

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Хайлт"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        hanz_list_rv = findViewById(R.id.hanz_list_rv)
        seach_et = findViewById(R.id.seach_et)

        hanz_list_rv?.layoutManager = GridLayoutManager(this, 1)

        //This will for default android divider
        // hanz_list_rv?.addItemDecoration(GridItemDecoration(10, 2))



        adapter = KanjiSearchAdapter(applicationContext)
        hanz_list_rv?.adapter = adapter
        adapter?.setMovieList(generateDummyData())
        adapter?.notifyDataSetChanged()

    }

    private fun generateDummyData(): List<Kanji> {
        mDb?.kanjiDao()?.loadAllKanjis()?.let { listOfKanji.addAll(it) }
        return listOfKanji
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Kanji> = ArrayList()
        for (item in listOfKanji) {
            if (item.kanji.contains(text) ||
                item.onyomi.contains(text) ||
                item.kunyomi.contains(text) ||
                item.desc.toLowerCase().contains(text.toLowerCase())
            ) {
                filteredList.add(item)
            }
        }
        adapter?.setMovieList(filteredList)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}