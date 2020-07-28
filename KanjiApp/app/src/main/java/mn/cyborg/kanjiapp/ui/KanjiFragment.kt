package mn.cyborg.kanjiapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_flash_cart.*
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.model.Kanji


class KanjiFragment : Fragment {

    var kanjis: Kanji? = null
    var types: List<String>? = null

    constructor(kanjis: Kanji?, types: List<String>?) : super() {
        this.kanjis = kanjis
        this.types = types
    }

    companion object {
        fun newInstance(kanji: Kanji, types: List<String>?): KanjiFragment {
            return KanjiFragment(kanji, types)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var root = inflater.inflate(R.layout.fragment_flash_cart, container, false)

        var kanjiTv = root.findViewById(R.id.item_kanji) as TextView
        var descTv = root.findViewById(R.id.item_desc) as TextView
        var onyomiTv = root.findViewById(R.id.item_on_yomi) as TextView
        var kunyomiTv = root.findViewById(R.id.item_kun_yomi) as TextView


        if (types!!.contains("1")) {
            kanjiTv.text = kanjis?.kanji
            kanjiTv.visibility = View.VISIBLE
        }else{
            kanjiTv.visibility = View.GONE
        }

        if (types!!.contains("2")) {
            onyomiTv.text = kanjis?.onyomi
            onyomiTv.visibility = View.VISIBLE
        }else{
            onyomiTv.visibility = View.GONE
        }

        if (types!!.contains("3")) {
            kunyomiTv.text = kanjis?.kunyomi
            kunyomiTv.visibility = View.VISIBLE
        }else{
            kunyomiTv.visibility = View.GONE
        }

        if (types!!.contains("4")) {
            descTv.text = kanjis?.desc
            descTv.visibility = View.VISIBLE
        }else{
            descTv.visibility = View.GONE
        }

        var kanjiTv1 = root.findViewById(R.id.item_kanji1) as TextView
        var descTv1 = root.findViewById(R.id.item_desc1) as TextView
        var onyomiTv1 = root.findViewById(R.id.item_on_yomi1) as TextView
        var kunyomiTv1 = root.findViewById(R.id.item_kun_yomi1) as TextView

        if (!types!!.contains("1")) {
            kanjiTv1.text = kanjis?.kanji
            kanjiTv1.visibility = View.VISIBLE
        }else{
            kanjiTv1.visibility = View.GONE
        }

        if (!types!!.contains("2")) {
            onyomiTv1.text = kanjis?.onyomi
            onyomiTv1.visibility = View.VISIBLE
        }else{
            onyomiTv1.visibility = View.GONE
        }

        if (!types!!.contains("3")) {
            kunyomiTv1.text = kanjis?.kunyomi
            kunyomiTv1.visibility = View.VISIBLE
        }else{
            kunyomiTv1.visibility = View.GONE
        }

        if (!types!!.contains("4")) {
            descTv1.text = kanjis?.desc
            descTv1.visibility = View.VISIBLE
        }else{
            descTv1.visibility = View.GONE
        }

        var cartView = root.findViewById(R.id.cartView) as CardView
        var mRelVis = root.findViewById(R.id.mRelVis) as RelativeLayout

        cartView.setOnClickListener {

            if (mRelVis.visibility == View.VISIBLE) {
                mRelVis.visibility = View.GONE

            } else {
                mRelVis.visibility = View.VISIBLE
            }

        }

        return root
    }

}