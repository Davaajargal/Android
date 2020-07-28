package mn.cyborg.kanjiapp.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_kanji.view.*
import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.ui.KanjiDetialActivity

import java.util.*

class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(context: Context, movieModel: Kanji) {
        itemView.item_kanji.text = movieModel.kanji
        itemView.item_on_yomi.text = movieModel.onyomi
        itemView.item_kun_yomi.text = movieModel.kunyomi
        itemView.item_desc.text = movieModel.desc

        itemView.setOnClickListener {

            val singlInt = Intent(context, KanjiDetialActivity::class.java)
            singlInt.putExtra("kanjiId", movieModel.id)
            singlInt.putExtra("lvlid", movieModel.lvl)
            singlInt.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            context.startActivity(singlInt)

        }
    }

}