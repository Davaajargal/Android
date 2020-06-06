package mn.cyborg.kanjiapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.ui.KanjiFragment


// 1
class FlashCartAdapter(fragmentManager: FragmentManager, private val movies: ArrayList<Kanji>, typesArr: List<String>) :
    FragmentStatePagerAdapter(fragmentManager) {

    var typesArr = typesArr

    // 2
    override fun getItem(position: Int): Fragment {
        return KanjiFragment.newInstance(movies[position], typesArr)
    }

    // 3
    override fun getCount(): Int {
        return movies.size
    }
}
