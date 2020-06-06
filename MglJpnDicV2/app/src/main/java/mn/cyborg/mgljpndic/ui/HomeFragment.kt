package mn.cyborg.mgljpndic.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.mgljpndic.R
import mn.cyborg.mgljpndic.adapter.WordAdapter
import mn.cyborg.mgljpndic.database.AppDatabase
import mn.cyborg.mgljpndic.model.Word
import mn.cyborg.mgljpndic.viewholder.WordViewHolder

class HomeFragment : Fragment(), WordViewHolder.ItemClickListener {

    private var TAG = "HomeFragment"

    private lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var progerssProgressDialog: ProgressDialog

    private var wordsList: ArrayList<Word> = ArrayList()
    private var recView: RecyclerView? = null

    private var adapter: WordAdapter? = null
    var searchView: EditText? = null

    var word: Word? = null

    private var mDb: AppDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        mDb = activity?.let { AppDatabase.getInstance(it) }
        recView = root.findViewById(R.id.word_list_rc)
        searchView = root.findViewById(R.id.search_view)
        initUI()

        // Log.e(TAG, "database all size : ${mDb?.wordDao()?.loadAllWord()?.size}")

        mDb?.wordDao()?.loadAllWord()?.let { wordsList.addAll(it) }
        adapter?.notifyDataSetChanged()

        searchView?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                filter(p0.toString())
            }
        })

//        val sortBtn = root.findViewById(R.id.sortbtn) as ImageButton
//        val kanjiBtn = root.findViewById(R.id.kanjibtn) as ImageButton

//        sortBtn.setOnClickListener {
//            onClearClicked()
//        }
//
//        kanjiBtn.setOnClickListener {
//            onDetectClicked()
//        }

        return root
    }

    private fun initUI() {

        progerssProgressDialog = ProgressDialog(activity)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)



        adapter = activity?.let { WordAdapter(it, this, wordsList) }
        linearLayoutManager = LinearLayoutManager(activity)
        recView?.layoutManager = linearLayoutManager
        recView?.adapter = adapter
        recView?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Word> = ArrayList()
        for (item in wordsList) {
            if (item.kanji.contains(text) ||
                item.hira.contains(text) ||
                item.dict.toLowerCase().contains(text.toLowerCase())
            ) {
                filteredList.add(item)
            }
        }
        adapter?.filterList(filteredList)
    }

    override fun onItemClick(view: View, position: Int) {
        word = wordsList[position]
        val intent = Intent(activity, WordDetialActivity::class.java)
        intent.putExtra("wordId", word?.id)
        startActivity(intent)
    }

}
