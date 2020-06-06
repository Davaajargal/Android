package mn.cyborg.mgljpndic.viewholder

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.mgljpndic.R

public class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // 独自に作成したListener
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    val itemTextView: TextView = view.findViewById(R.id.list_title)
    val itemTextDesc: TextView = view.findViewById(R.id.list_description)
    val itemTexthira: TextView = view.findViewById(R.id.list_title_hira)
    val speechBtn: Button = view.findViewById(R.id.speechBtn)

    init {
        // layoutの初期設定するときはココ
    }

}