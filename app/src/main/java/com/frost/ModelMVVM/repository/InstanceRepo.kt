package com.frost.ModelMVVM.repository

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class InstanceRepo {

    companion object{
        val baseURL = "https://api.estadisticasbcra.com/"

        fun getRetofitInstance(): Retrofit =
            Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }
}