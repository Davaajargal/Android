package mn.cyborg.kanjiapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_flash_setting.*
import mn.cyborg.kanjiapp.R

class FlashCartSettingActivity : AppCompatActivity() {

    var TAG = "FlashCartSettingActivity"

    var lvlid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_flash_setting)

        lvlid = intent.getIntExtra("lvlid", 0)
        initUI()

        flashcartbtn.setOnClickListener {

            var types = ""
            var lvls = ""

            if(hanzChck.isChecked){
                types += "1,"
            }

            if(onCheck.isChecked){
                types += "2,"
            }

            if(kunCheck.isChecked){
                types += "3,"
            }

            if(descCheck.isChecked){
                types += "4,"
            }

            if(n5Chck.isChecked){
                lvls += "5,"
            }

            if(n4Check.isChecked){
                lvls += "4,"
            }

            if(n3Check.isChecked){
                lvls += "3,"
            }

            if(n2Check.isChecked){
                lvls += "2,"
            }

            if(n1Check.isChecked){
                lvls += "1,"
            }

            val flashInt = Intent(applicationContext, FlashCartActivity::class.java)
            flashInt.putExtra("lvlid", lvlid)
            flashInt.putExtra("lvls", lvls)
            flashInt.putExtra("types", types)
            startActivity(flashInt)
        }

    }


    fun initUI() {

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "N$lvlid"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}