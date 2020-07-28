package mn.cyborg.mgljpndic.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import mn.cyborg.mgljpndic.R
import mn.cyborg.mgljpndic.model.Word
import mn.cyborg.mgljpndic.ui.WordDetialActivity
import mn.cyborg.mgljpndic.viewholder.WordViewHolder
import java.util.*


class WordAdapter(
    private val context: Context,
    private val itemClickListener: WordViewHolder.ItemClickListener,
    private var itemList: List<Word>
) : RecyclerView.Adapter<WordViewHolder>(), TextToSpeech.OnInitListener {

    private var mRecyclerView: RecyclerView? = null
    private var tts = TextToSpeech(context, this)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.let {

            val word = itemList[position]

            it.itemTextView.text = word.kanji
            it.itemTextDesc.text = word.dict
            it.itemTexthira.text = "[${word.hira}]"
            it.speechBtn.setOnClickListener {
                tts.speak(word.kanji, TextToSpeech.QUEUE_FLUSH, null,"")
            }

            it.mainRel.setOnClickListener {

                val intent = Intent(context, WordDetialActivity::class.java)
                intent.putExtra("wordId", word.id)
                context.startActivity(intent)

            }

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

    fun filterList(filteredList: List<Word>) {
        itemList = filteredList
        notifyDataSetChanged()
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts.setLanguage(Locale.JAPANESE)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
                // Shutdown TTS
                if (tts != null) {
                    tts!!.stop()
                    tts!!.shutdown()
                }
            } else {
                // !!.isEnabled = true
                // Shutdown TTS
                if (tts != null) {
                    tts!!.stop()
                    tts!!.shutdown()
                }
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
            // Shutdown TTS
            if (tts != null) {
                tts!!.stop()
                tts!!.shutdown()
            }
        }

    }

}