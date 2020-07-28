package mn.cyborg.mgljpndic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.mgljpndic.R
import mn.cyborg.mgljpndic.model.SavedWord
import mn.cyborg.mgljpndic.model.Word
import mn.cyborg.mgljpndic.viewholder.WordViewHolder


class SavedWordAdapter(
    private val context: Context,
    private val itemClickListener: WordViewHolder.ItemClickListener,
    private var itemList: List<SavedWord>
) : RecyclerView.Adapter<WordViewHolder>() {

    private var mRecyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.let {

            val word = itemList[position]

            it.itemTextView.text = word.kanji
            it.itemTextDesc.text = word.dict
            it.itemTexthira.text = "[${word.hira}]"
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val mView = layoutInflater.inflate(R.layout.item_word_rec, parent, false)

        mView.setOnClickListener { view ->
            mRecyclerView?.let {
                itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
            }
        }

        return WordViewHolder(mView)
    }

}