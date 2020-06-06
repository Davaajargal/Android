package mn.cyborg.mgljpndic.http


import mn.cyborg.mgljpndic.model.Word
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("getword")
    fun getPhotos(
        @Field("id") flg: Int
    ): Call<List<Word>>

    @FormUrlEncoded
    @POST("word")
    fun addWord(
        @Field("kanji") kanji: String,
        @Field("hira") hira: String,
        @Field("dict") dict: String,
        @Field("jpnslvl") jpnslvl: String,
        @Field("type") type: String,
        @Field("typelvl") typelvl: String,
        @Field("eng") eng: String
    ): Call<Word>

}