package com.frost.ModelMVVM.repository

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class InstanceRepo {

    companion object{
        const val baseURL = "https://api.estadisticasbcra.com/"
        private val interceptor = TokenInterceptor()

        private val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        fun getRetofitInstance(): Retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}