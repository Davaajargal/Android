package mn.cyborg.mgljpndic.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.mgljpndic.R
import mn.cyborg.mgljpndic.adapter.SavedWordAdapter
import mn.cyborg.mgljpndic.database.AppDatabase
import mn.cyborg.mgljpndic.model.SavedWord
import mn.cyborg.mgljpndic.viewholder.WordViewHolder

class SavedWordFragment : Fragment(), WordViewHolder.ItemClickListener {

    private var wordsList: ArrayList<SavedWord> = ArrayList()
    private var recView: RecyclerView? = null

    private lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: SavedWordAdapter? = null

    private var mDb: AppDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_savedword, container, false)
        recView = root.findViewById(R.id.savedwordrc)
        mDb = activity?.let { AppDatabase.getInstance(it) }

        adapter = activity?.let { SavedWordAdapter(it, this, wordsList) }
        linearLayoutManager = LinearLayoutManager(activity)
        recView?.layoutManager = linearLayoutManager
        recView?.adapter = adapter
        recView?.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        return root
    }

    override fun onItemClick(view: View, position: Int) {
        val word = wordsList[position]
        val intent = Intent(activity, WordDetialActivity::class.java)
        intent.putExtra("wordId", word.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        wordsList.clear()
        mDb?.savedWordDao()?.loadAllWord()?.let { wordsList.addAll(it) }
        adapter?.notifyDataSetChanged()

    }

}
