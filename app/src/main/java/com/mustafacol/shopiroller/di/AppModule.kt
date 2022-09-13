package com.mustafacol.shopiroller.di

import com.mustafacol.shopiroller.constants.Constants
import com.mustafacol.shopiroller.network.ShopirollerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShopirollerApi():ShopirollerApi{
        val builder= OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val request =
                chain.request().newBuilder().addHeader("Api-Key", Constants.API_KEY).build()
            chain.proceed(request)
        }
        builder.addInterceptor{chain ->
            val request =
                chain.request().newBuilder().addHeader("Alias-Key", Constants.ALIAS_KEY).build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopirollerApi::class.java)
    }
}