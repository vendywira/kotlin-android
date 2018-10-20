package app.learn.kotlin.network

import android.util.Log
import app.learn.kotlin.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @Reusable
    internal fun provideSportService(retrofit: Retrofit): TheSportDBApiService {
        return retrofit.create(TheSportDBApiService::class.java)
    }

    @Provides
    @Reusable
    internal fun provideRetrofit(): Retrofit {
        val httpInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.e("football club", it)
        })
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpInterceptor).build()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build()
    }
}