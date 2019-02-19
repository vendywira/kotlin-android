package app.learn.kotlin.koin

import android.util.Log
import app.learn.kotlin.BuildConfig
import app.learn.kotlin.network.TheSportDBApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { createOkHttpClient() }
    factory { apiService<TheSportDBApiService>(get(), BuildConfig.BASE_URL) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
        Log.e("football club", it)
    })
    httpInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpInterceptor).build()
}

inline fun <reified T> apiService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
