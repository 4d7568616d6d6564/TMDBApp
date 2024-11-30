package com.mkdevelopment.tmdbapp.di

import com.mkdevelopment.tmdbapp.BuildConfig
import com.mkdevelopment.tmdbapp.network.ApiService
import com.mkdevelopment.tmdbapp.util.Constants.Companion.BASE_URL
import com.mkdevelopment.tmdbapp.util.Constants.Companion.OKHTTP_MAX_RETRIES
import com.mkdevelopment.tmdbapp.util.Constants.Companion.OKHTTP_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        val retryInterceptor = RetryInterceptor()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(retryInterceptor)
            .connectTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(OKHTTP_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    class RetryInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var tryCount = 0
            var response: Response
            var exception: IOException? = null

            while (tryCount < OKHTTP_MAX_RETRIES) {
                try {
                    response = chain.proceed(chain.request())
                    if (response.isSuccessful) {
                        return response
                    }
                } catch (e: IOException) {
                    exception = e
                    tryCount++
                }
            }
            throw exception ?: IOException("Bir hata oluştu")
        }
    }
}