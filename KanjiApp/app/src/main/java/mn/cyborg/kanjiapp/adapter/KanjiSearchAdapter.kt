package mn.cyborg.kanjiapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.model.Kanji

class KanjiSearchAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = listOf<Kanji>()
    private var context = context;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_kanji, parent, false)
        )
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val movieViewHolder = viewHolder as MovieListViewHolder
        movieViewHolder.bindView(context, itemList[position])

    }

    fun setMovieList(listOfMovies: List<Kanji>) {
        itemList = listOfMovies
        notifyDataSetChanged()
    }

}