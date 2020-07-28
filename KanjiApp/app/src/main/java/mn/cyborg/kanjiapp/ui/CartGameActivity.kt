package mn.cyborg.kanjiapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mn.cyborg.kanjiapp.R
import mn.cyborg.kanjiapp.adapter.CardGameAdapter

class CartGameActivity : AppCompatActivity() {

    private var gameAdapter: CardGameAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game_cart)

        initUI()

    }

    private fun initUI(){

    }

}