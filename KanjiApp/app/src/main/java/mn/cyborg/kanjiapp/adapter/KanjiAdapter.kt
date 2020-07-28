package mn.cyborg.kanjiapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.model.Kanji

class KanjiAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfMovies = listOf<Kanji>()
    private var context = context;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_kanji, parent, false)
        )
    }

    override fun getItemCount(): Int = listOfMovies.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val movieViewHolder = viewHolder as MovieListViewHolder
        movieViewHolder.bindView(context, listOfMovies[position])

    }

    fun setMovieList(listOfMovies: List<Kanji>) {
        this.listOfMovies = listOfMovies
        notifyDataSetChanged()
    }
}