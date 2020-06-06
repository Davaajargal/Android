package mn.cyborg.kanjiapp.http


import mn.cyborg.kanjiapp.model.Kanji
import mn.cyborg.kanjiapp.model.Word
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("getkanji")
    fun getPhotos(
        @Field("id") flg: Int
    ): Call<List<Kanji>>

    @FormUrlEncoded
    @POST("getword")
    fun getWords(
        @Field("id") flg: Int
    ): Call<List<Word>>

    @FormUrlEncoded
    @POST("kanji")
    fun addWord(
        @Field("kanji") kanji: String,
        @Field("onyomi") hira: String,
        @Field("kunyomi") dict: String,
        @Field("desc") jpnslvl: String,
        @Field("eng") type: String,
        @Field("lvl") typelvl: Int
    ): Call<Kanji>

}