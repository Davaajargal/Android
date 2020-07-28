package mn.cyborg.kanjiapp.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.kanjiapp.R

public class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemTextView: TextView = view.findViewById(R.id.list_title)
    val itemTextDesc: TextView = view.findViewById(R.id.list_description)
    val itemTexthira: TextView = view.findViewById(R.id.list_title_hira)
//    val itemImageView: ImageView = view.findViewById(R.id.itemImageView)

    init {
        // layoutの初期設定するときはココ
    }

}