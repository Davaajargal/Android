package mn.cyborg.mgljpndic.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.ceryle.radiorealbutton.RadioRealButtonGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_new_word.*
import mn.cyborg.mgljpndic.R
import mn.cyborg.mgljpndic.http.ApiClient
import mn.cyborg.mgljpndic.model.Word
import mn.cyborg.mgljpndic.utils.Utils
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewWordActivity : AppCompatActivity() {

    private var TAG = "NewWordActivity"
    private var db = FirebaseFirestore.getInstance()
    private var typeRb: RadioRealButtonGroup? = null
    private var lvlRb: RadioRealButtonGroup? = null

    private var typeTitlTxt: String? = null
    private var typeTxt: String? = null
    private var lvlTxt: String? = null
    lateinit var progerssProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_word)

        typeRb = findViewById<View>(R.id.type_radio_btn) as RadioRealButtonGroup
        lvlRb = findViewById<View>(R.id.lvl_rbtn) as RadioRealButtonGroup

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Үг нэмэх"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        progerssProgressDialog = ProgressDialog(applicationContext)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)

        typeRb?.setOnClickedButtonListener { button, position ->
            typeTxt = position.toString()
            typeTitlTxt = button.text
        }

        lvlRb?.setOnClickedButtonListener { _, position ->
            lvlTxt = position.toString()
        }

        save_btn.setOnClickListener {

            insertForFile()

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun userForInsert() {

        if (hanz_et.text.toString().isNotEmpty() && hira_et.text.toString()
                .isNotEmpty() && dict_et.text.toString()
                .isNotEmpty()
        ) {

            val kanji = hanz_et.text.toString()
            val hira = hira_et.text.toString()
            val dict = dict_et.text.toString()

            val call: Call<Word> =
                ApiClient.getClient.addWord(
                    kanji,
                    hira,
                    dict.toLowerCase(),
                    lvlTxt.toString(),
                    typeTxt.toString(),
                    typeTitlTxt.toString(),
                    "hj"
                )
            call.enqueue(object : Callback<Word> {

                override fun onResponse(
                    call: Call<Word>?,
                    response: Response<Word>?
                ) {
                    progerssProgressDialog.dismiss()

                    Toast.makeText(
                        applicationContext,
                        "Хадгалагдлаа. Шинээр үг нэмсэнд баярлалаа.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                }

                override fun onFailure(call: Call<Word>?, t: Throwable?) {
                    progerssProgressDialog.dismiss()
                    t?.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "Алдаа гарлаа. Дахин оролдоно уу.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            })
        }

    }

    fun insertForFile() {


        val jsonFileString: String = Utils.getJsonFromAssets(applicationContext, "words.json")

        Log.e(TAG, jsonFileString)

        val jsonArr = JSONArray(jsonFileString)
        val b = jsonArr.length()

        for (i in 0 until b) {


            var handler = Handler()
            val r = Runnable {

                val categories = jsonArr.getJSONObject(i)

                val kanji = categories.optString("example_jp")
                val hira = categories.optString("example_yomi")
                val dict = categories.optString("example_mon")
                val lvl = "3"
                val type = "2"
                val typetitle = null
                val eng = categories.optString("example_eng")

                val call: Call<Word> =
                    ApiClient.getClient.addWord(
                        kanji,
                        hira,
                        dict.toLowerCase(),
                        lvl,
                        type,
                        typetitle.toString(),
                        eng
                    )
                call.enqueue(object : Callback<Word> {

                    override fun onResponse(
                        call: Call<Word>?,
                        response: Response<Word>?
                    ) {
//                        progerssProgressDialog.dismiss()

                        Log.e(TAG, response?.body()?.kanji)


                    }

                    override fun onFailure(call: Call<Word>?, t: Throwable?) {
                        t?.printStackTrace()
                    }

                })

            }
            handler.postDelayed(r, 1000)

        }

    }

}