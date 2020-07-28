package mn.cyborg.kanjiapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.model.Word
import mn.cyborg.kanjiapp.viewholder.GameViewHolder
import mn.cyborg.kanjiapp.viewholder.WordViewHolder

class CardGameAdapter(
    private val context: Context,
    private val itemClickListener: WordViewHolder.ItemClickListener,
    private var itemList: List<Word>
) : RecyclerView.Adapter<GameViewHolder>() {

    private var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.let {

            val word = itemList[position]

            it.itemTitleTv.text = word.kanji
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.item_word_rec, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return GameViewHolder(mView)
    }

    
}