package mn.cyborg.kanjiapp.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.kanjiapp.R

class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemTitleTv: TextView = view.findViewById(R.id.itemgamecart)



}