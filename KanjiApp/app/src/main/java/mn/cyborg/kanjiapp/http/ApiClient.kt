package mn.cyborg.kanjiapp.http

import android.icu.util.TimeUnit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


//object ApiClient {
//    var BASE_URL: String = "https://ekv.mn/mgljpndic/public/"
//    val getClient: ApiInterface
//        get() {
//
//            val gson = GsonBuilder()
//                .setLenient()
//                .create()
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//
//            return retrofit.create(ApiInterface::class.java)
//
//        }
//
//}

open class ApiClient {
    companion object {
        private const val ENDPOINT = "https://www.api.zulzagateam.com/"
        private val TAG = ApiClient::class.simpleName

        val apiClient: ApiInterface
            get() = Retrofit.Builder()
                .client(getClient())
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(ApiInterface::class.java)

        private fun getClient(): OkHttpClient {
            return OkHttpClient
                .Builder()
                .connectTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        }
    }
}