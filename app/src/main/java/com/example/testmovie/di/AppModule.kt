package com.example.testmovie.di

import com.example.testmovie.BuildConfig
import com.example.testmovie.network.AuthInterceptor
import com.example.testmovie.network.MovieServiceInstance
import com.example.testmovie.network.TvServiceInstance
import com.example.testmovie.utils.Constants
import com.example.testmovie.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor) = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideTv(retrofit: Retrofit) = retrofit.create(TvServiceInstance::class.java)

    @Provides
    @Singleton
    fun provideMovie(retrofit: Retrofit) = retrofit.create(MovieServiceInstance::class.java)

}