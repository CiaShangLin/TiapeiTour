package com.shang.taipeitour.di

import com.shang.taipeitour.network.TaipeiTourApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module {

    //zh-tw/Attractions/All?page=1
    single {
        Retrofit.Builder()
            .baseUrl("https://www.travel.taipei/open-api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
    }

    factory {
        OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    factory {
        (get() as Retrofit).create(TaipeiTourApi::class.java)
    }
}