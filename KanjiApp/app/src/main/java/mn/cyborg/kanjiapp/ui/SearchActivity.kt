package mn.cyborg.kanjiapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_kanji_list.*
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.adapter.KanjiSearchAdapter
import mn.cyborg.kanjiapp.database.AppDatabase
import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.util.GridItemDecoration

class SearchActivity : AppCompatActivity() {

    private var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        mDb =  AppDatabase.getInstance(applicationContext)

        initView()

    }

    private fun initView() {

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Хайлт"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        kanji_rec.layoutManager = GridLayoutManager(this, 4)

        //This will for default android divider
        kanji_rec.addItemDecoration(GridItemDecoration(10, 2))

        val movieListAdapter = KanjiSearchAdapter(applicationContext)
        kanji_rec.adapter = movieListAdapter
        movieListAdapter.setMovieList(generateDummyData())
        movieListAdapter.notifyDataSetChanged()
    }

    private fun generateDummyData(): List<Kanji> {
        val listOfKanji = mutableListOf<Kanji>()
        mDb?.kanjiDao()?.loadAllKanjis()?.let { listOfKanji.addAll(it) }
        return listOfKanji
    }

}