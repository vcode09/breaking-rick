package com.vmoreno.butterfly.rick.datai.di

import com.vmoreno.butterfly.rick.data.network.Api
import com.vmoreno.butterfly.rick.data.network.BASE_URL
import com.vmoreno.butterfly.rick.data.network.NETWORK_TIME_OUT
import com.vmoreno.butterfly.rick.data.network.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { createService(get(), Api::class.java) }
    single { createRetrofit(get(), BASE_URL) }
    single { createOkHttpClient() }
    single { MoshiConverterFactory.create() }
    single { Moshi.Builder().build() }
}

fun createOkHttpClient(
    vararg interceptors: Interceptor = arrayOf(
        HttpLoggingInterceptor().apply { level = BODY }
    )
): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()
        .connectTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIME_OUT, TimeUnit.SECONDS)
    interceptors.forEach {
        clientBuilder.addInterceptor(it)
    }
    return clientBuilder.build()
}

fun createRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

fun <T> createService(retrofit: Retrofit, modelClass: Class<T>): T {
    return retrofit.create(modelClass)
}